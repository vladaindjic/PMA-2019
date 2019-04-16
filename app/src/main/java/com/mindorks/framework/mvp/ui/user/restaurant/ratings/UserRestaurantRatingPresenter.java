package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserRestaurantRatingPresenter<V extends UserRestaurantRatingMvpView> extends BasePresenter<V>
        implements UserRestaurantRatingMvpPresenter<V> {

    private static final String TAG = "UserRestaurantRatingPresenter";

    @Inject
    public UserRestaurantRatingPresenter(DataManager dataManager,
                                         SchedulerProvider schedulerProvider,
                                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long restaurantId) {

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getRestaurantRatingApiCall(restaurantId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantRatingResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantRatingResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantRating(response.getData());
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
