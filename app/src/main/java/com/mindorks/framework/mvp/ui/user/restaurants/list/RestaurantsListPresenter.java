package com.mindorks.framework.mvp.ui.user.restaurants.list;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.KitchenDB;
import com.mindorks.framework.mvp.data.db.model.MyRestaurantDB;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.FilterRestaurantRequest;
import com.mindorks.framework.mvp.data.network.model.MyRestaurantsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
            UserRestaurantsActivity activity = (UserRestaurantsActivity) fragment.getBaseActivity();
            final String query = activity.getSearchQuery();

            // show all restaurants
            getCompositeDisposable().add(getDataManager().getUserFilter(getDataManager().getActiveUserFilterId())
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
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
                    }));

        } else if (whatToPrepare == PREPARE_MY_RESTAURANTS) {

            // check if there is internet connection
            if (getMvpView().isNetworkConnected()) {
                // show my restaurants
                getCompositeDisposable().add(getDataManager().getSubscriptionsApiCall()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<MyRestaurantsResponse>() {
                            @Override
                            public void accept(@NonNull MyRestaurantsResponse response)
                                    throws Exception {
                                if (response != null && response.getData() != null) {
                                    // prikazujemo restorane
                                    getMvpView().updateRestaurantsList(response.getRestaurantsFromMyRestaurant());
                                    // cuvamo ih u lokalu
                                    cacheMyRestaurants(response);
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
            } else {
                // povlacenje kesiranih restorana iz baze
                getCompositeDisposable().add(getDataManager()
                        .getAllMyRestaurants()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(
                        new Consumer<List<MyRestaurantDB>>() {
                            @Override
                            public void accept(@NonNull List<MyRestaurantDB> myRestaurantDBList)
                                    throws Exception {
                                if (myRestaurantDBList != null && myRestaurantDBList.size() > 0) {
                                    List<RestaurantsResponse.Restaurant> restaurantList = new ArrayList<>();
                                    for(MyRestaurantDB mrdb: myRestaurantDBList) {
                                        restaurantList.add(mrdb.getRestaurantFromMyRestaurantDB());
                                    }

                                    // prikazujemo kesirane restorane
                                    getMvpView().updateRestaurantsList(restaurantList);
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
                        }
                ));

            }


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

    private void cacheMyRestaurants(final MyRestaurantsResponse myRestaurantsResponse) {
        getCompositeDisposable().add(getDataManager()
                .deleteAllKitchensDB()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    getDataManager()
                            .deleteAllMyRestaurants()
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                MyRestaurantDB mrdb;
                                // cuvamo jedan po jedan MyRestaurantDB
                                for (final MyRestaurantsResponse.MyRestaurant mr : myRestaurantsResponse.getData()) {
                                    // dobijamo podatke koji se upisuju u lokalnu bazu
                                    mrdb = mr.getMyRestaurantDBFromMyRestaurant();
                                    getDataManager()
                                            .saveMyRestaurant(mrdb)
                                            .subscribeOn(getSchedulerProvider().io())
                                            .observeOn(getSchedulerProvider().ui())
                                            .subscribe(new Consumer<Long>() {
                                        @Override
                                        public void accept(Long newMyRestaurantDBId) throws Exception {
                                            // sada cuvamo sve kuhinje
                                            KitchenDB kdb;
                                            for (RestaurantDetailsResponse.Kitchen k : mr.getKitchens()) {
                                                kdb = k.getKitchenDB();
                                                kdb.setMyRestaurantId(newMyRestaurantDBId);
                                                getDataManager()
                                                        .saveKitchenDB(kdb)
                                                        .subscribeOn(getSchedulerProvider().io())
                                                        .observeOn(getSchedulerProvider().ui())
                                                        .subscribe(new Consumer<Long>() {
                                                    @Override
                                                    public void accept(Long newKitchenDBId) throws Exception {
                                                        System.out.println("New kitchen saved: " + newKitchenDBId);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            } else {
                                System.out.println("VI3: NE VALJA cacheMyRestaurants " +
                                        "MyRestaurantsDB delete all");
                            }
                        }
                    });
                } else {
                    System.out.println("VI3: NE VALJA: MyRestaurantsDB delete all");
                }
            }
        }));
    }

}
