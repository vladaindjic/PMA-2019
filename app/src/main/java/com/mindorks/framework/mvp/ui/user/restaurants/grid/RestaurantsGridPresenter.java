package com.mindorks.framework.mvp.ui.user.restaurants.grid;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.FilterRestaurantRequest;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class RestaurantsGridPresenter<V extends RestaurantsGridMvpView> extends BasePresenter<V>
        implements RestaurantsGridMvpPresenter<V> {

    @Inject
    public RestaurantsGridPresenter(DataManager dataManager,
                                    SchedulerProvider schedulerProvider,
                                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(final Double latitude, final Double longitude) {

        getMvpView().showLoading();

        Long userFilterId = getDataManager().getActiveUserFilterId();
        System.out.println("Nasisi se kurcine " + userFilterId + " " + latitude + " " + longitude);

        RestaurantsGridFragment fragment = (RestaurantsGridFragment)getMvpView();
        UserRestaurantsActivity activity = (UserRestaurantsActivity)fragment.getBaseActivity();
        final String query = activity.getSearchQuery();

        getDataManager().getUserFilter(getDataManager().getActiveUserFilterId())
                .subscribe(new Consumer<UserFilter>() {
                    @Override
                    public void accept(UserFilter userFilter) throws Exception {
                        getRestaurantsUsingFilter(new FilterRestaurantRequest(
                                query,
                                userFilter, latitude, longitude));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getRestaurantsUsingFilter(new FilterRestaurantRequest(query, null,
                                latitude, longitude));
                    }
                });



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
