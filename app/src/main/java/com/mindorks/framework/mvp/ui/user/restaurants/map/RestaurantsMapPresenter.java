package com.mindorks.framework.mvp.ui.user.restaurants.map;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class RestaurantsMapPresenter<V extends RestaurantsMapMvpView> extends BasePresenter<V>
        implements RestaurantsMapMvpPresenter<V> {

    @Inject
    public RestaurantsMapPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);


    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();

        getDataManager().getUserFilter(getDataManager().getActiveUserFilterId())
                .subscribe(new Consumer<UserFilter>() {
                    @Override
                    public void accept(UserFilter userFilter) throws Exception {
                        getRestaurantsUsingFilter(userFilter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getRestaurantsUsingFilter(null);
                    }
                });

    }

    private void getRestaurantsUsingFilter(UserFilter userFilter) {
        getCompositeDisposable().add(getDataManager()
                .getRestaurantsApiCall(userFilter)
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
