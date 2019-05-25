package com.mindorks.framework.mvp.ui.user.restaurant.details;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserRestaurantDetailsPresenter<V extends UserRestaurantDetailsMvpView> extends BasePresenter<V>
        implements UserRestaurantDetailsMvpPresenter<V> {

    private static final String TAG = "UserDishDetailsPresenter";

    @Inject
    public UserRestaurantDetailsPresenter(DataManager dataManager,
                                          SchedulerProvider schedulerProvider,
                                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long restaurantId) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getRestaurantDetailsApiCall(restaurantId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantDetailsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantDetailsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantDetails(response.getData());
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

    @Override
    public void subscribeToRestaurant(Long restaurantId) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .putRestaurantSubscribeApiCall(restaurantId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantDetailsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantDetailsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            // oznacavamo da se korisnik uspesno prijavio
                            getMvpView().successSubscription();
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
                        // ponistavamo vrednost zvezdice
                        getMvpView().failSubscription();
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
