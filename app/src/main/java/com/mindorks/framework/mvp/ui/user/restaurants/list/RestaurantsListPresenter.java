package com.mindorks.framework.mvp.ui.user.restaurants.list;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.FilterRestaurantRequest;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.grid.RestaurantsGridFragment;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class RestaurantsListPresenter<V extends RestaurantsListMvpView> extends BasePresenter<V>
        implements RestaurantsListMvpPresenter<V> {

    @Inject
    public RestaurantsListPresenter(DataManager dataManager,
                                    SchedulerProvider schedulerProvider,
                                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);


    }

    @Override
    public void onViewPrepared(int whatToPrepare, final Double latitude, final Double longitude) {
        getMvpView().showLoading();

        if (whatToPrepare == PREPARE_ALL_RESTAURANTS) {
            RestaurantsListFragment fragment = (RestaurantsListFragment) getMvpView();
            UserRestaurantsActivity activity = (UserRestaurantsActivity)fragment.getBaseActivity();
            final String query = activity.getSearchQuery();

            // show all restaurants
            getDataManager().getUserFilter(getDataManager().getActiveUserFilterId())
                    .subscribe(new Consumer<UserFilter>() {
                        @Override
                        public void accept(UserFilter userFilter) throws Exception {
                            getRestaurantsUsingFilter(new FilterRestaurantRequest(query,
                                    userFilter, latitude, longitude));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getRestaurantsUsingFilter(new FilterRestaurantRequest(query, null,
                                    latitude, longitude));
                        }
                    });
        } else if (whatToPrepare == PREPARE_MY_RESTAURANTS) {
            // show my restaurants
            getCompositeDisposable().add(getDataManager().getSubscriptionsApiCall()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<RestaurantsResponse>() {
                        @Override
                        public void accept(@NonNull RestaurantsResponse response)
                                throws Exception {
                            if (response != null && response.getData() != null) {
                                getMvpView().updateRestaurantsList(response.getData());
                            }
                            getMvpView().hideLoading();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable)
                                throws Exception {
                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                            // handle the error here
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        }
                    }));

        }

    }

    private void getRestaurantsUsingFilter(FilterRestaurantRequest filterRestaurantRequest) {
        getCompositeDisposable().add(getDataManager()
                .getRestaurantsApiCall(filterRestaurantRequest)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantsList(response.getData());
                        }
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));

    }
}
