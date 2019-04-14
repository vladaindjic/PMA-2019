package com.mindorks.framework.mvp.ui.user.restaurants.map;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
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


//        List<RestaurantsResponse.Restaurant> restaurants = new ArrayList<>();
//        restaurants.add(new RestaurantsResponse.Restaurant(1L, "Restoran " +
//                "1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbasdakjs;dkas;ldka;sldka;lsdklkjlkjlkqqqqqqqqqqqqqqqqqqqqqqqqqqqvvvv",
//                "https://s3.ap-south-1.amazonaws.com/mindorks/blogs/mindorks-blog-learn-kotlin%E2%80%8A%E2%80%94%E2%80%8Alateinit-vs-lazy.png"));
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

                            // FIXME vi3: privremeno resenje dok se ne ubaci u response
                            List<RestaurantsResponse.Restaurant> restaurantList =
                                    response.getData();

                            // imamo 10 restorana u response-u
                            restaurantList.get(0).setLatitude(28.583911);
                            restaurantList.get(0).setLongitude(77.319116);
                            restaurantList.get(0).setAddress("Adresa 1");

                            restaurantList.get(1).setLatitude(28.583078);
                            restaurantList.get(1).setLongitude(77.313744);
                            restaurantList.get(1).setAddress("Adresa 2");

                            restaurantList.get(2).setLatitude(28.580903);
                            restaurantList.get(2).setLongitude(77.317408);
                            restaurantList.get(2).setAddress("Adresa 3");

                            restaurantList.get(3).setLatitude(28.580108);
                            restaurantList.get(3).setLongitude(77.315271);
                            restaurantList.get(3).setAddress("Adresa 4");


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
