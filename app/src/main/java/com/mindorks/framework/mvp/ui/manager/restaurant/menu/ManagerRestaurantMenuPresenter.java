package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.manager.RestaurantDishesResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ManagerRestaurantMenuPresenter<V extends ManagerRestaurantMenuMvpView> extends BasePresenter<V>
        implements ManagerRestaurantMenuMvpPresenter<V> {

    private static final String TAG = "ManagerRestaurantMenuPresenter";


    @Inject
    public ManagerRestaurantMenuPresenter(DataManager dataManager,
                                          SchedulerProvider schedulerProvider,
                                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();


        // TODO vi3: REST API call
        getCompositeDisposable().add(getDataManager()
                .getRestaurantMenuApiCall(getDataManager().getRestaurantIdManager())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MenuResponse>() {
                    @Override
                    public void accept(@NonNull MenuResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateMenu(response.getData());
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
    public void getAllRestaurantDishes() {
        getMvpView().showLoading();
        // zovemo ipak Cook
        getCompositeDisposable().add(getDataManager()
                .getRestaurantCookApiCall(getDataManager().getRestaurantIdManager())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantCookResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantCookResponse response)
                            throws Exception {
                        if (response != null) {
                            RestaurantCookResponse.RestaurantCook cook = response.getData();
                            if (cook != null) {
                                if (cook.getRestaurantCookItemList() != null) {
                                    List<MenuResponse.Dish> dishList = new ArrayList<>();
                                    MenuResponse.Dish dish;
                                    for (RestaurantCookResponse.RestaurantCook.RestaurantCookItem rci: cook.getRestaurantCookItemList()) {
                                        dish = new MenuResponse.Dish();
                                        dish.setId(rci.getId());
                                        dish.setImgUrl(rci.getImgUrl());
                                        dish.setName(rci.getName());
                                        dish.setPrice(rci.getPrice());
                                        dishList.add(dish);
                                    }
                                    getMvpView().updateAllRestaurantDishes(dishList);
                                }
                            }
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
    public void submitMenu(MenuResponse.Menu menu) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .putMenuApiCall(menu)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MenuResponse>() {
                    @Override
                    public void accept(@NonNull MenuResponse response)
                            throws Exception {
                        // TODO vi3: odraditi i prikaz gresaka, ako je update neuspesan
                        if (response != null && response.getData() != null) {
                            getMvpView().updateMenu(response.getData());
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
