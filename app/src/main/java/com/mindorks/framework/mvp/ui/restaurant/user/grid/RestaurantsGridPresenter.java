package com.mindorks.framework.mvp.ui.restaurant.user.grid;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

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
    public void onViewPrepared() {

        getMvpView().showLoading();


//        List<RestaurantsResponse.Restaurant> restaurants = new ArrayList<>();
//        restaurants.add(new RestaurantsResponse.Restaurant(1L, "Restoran 1", "https://s3.ap-south-1.amazonaws.com/mindorks/blogs/mindorks-blog-learn-kotlin%E2%80%8A%E2%80%94%E2%80%8Alateinit-vs-lazy.png"));
//        restaurants.add(new RestaurantsResponse.Restaurant(1L, "Restoran 2", "https://s3.ap-south-1.amazonaws.com/mindorks/blogs/mindorks-blog-learn-kotlin%E2%80%8A%E2%80%94%E2%80%8Alateinit-vs-lazy.png"));
//        restaurants.add(new RestaurantsResponse.Restaurant(1L, "Restoran 3", "https://s3.ap-south-1.amazonaws.com/mindorks/blogs/mindorks-blog-learn-kotlin%E2%80%8A%E2%80%94%E2%80%8Alateinit-vs-lazy.png"));
//
//        getMvpView().updateRestaurantsList(restaurants);
//        getMvpView().hideLoading();


        getCompositeDisposable().add(getDataManager()
                .getRestaurantsApiCall()
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
