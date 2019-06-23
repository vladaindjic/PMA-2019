package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.details;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ManagerDailyMenuDetailsPresenter<V extends ManagerDailyMenuDetailsMvpView>
        extends BasePresenter<V> implements ManagerDailyMenuDetailsMvpPresenter<V> {

    private static final String TAG = "ManagerDailyMenuDetailsPresenter";


    @Inject
    public ManagerDailyMenuDetailsPresenter(DataManager dataManager,
                                            SchedulerProvider schedulerProvider,
                                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void prepareDishForAutocomplete() {

        Long restaurantId = -1L;
        restaurantId = getDataManager().getRestaurantIdManager();

        getCompositeDisposable().add(getDataManager()
                .getRestaurantCookApiCall(restaurantId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantCookResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantCookResponse response)
                            throws Exception {
                        System.out.println(response);
                        System.out.println(response.getData().getRestaurantCookItemList().size());
                        if (response != null && response.getData() != null) {

                            List<MenuResponse.Dish> dishList = new ArrayList<>();
                            for (RestaurantCookResponse.RestaurantCook.RestaurantCookItem dish : response.getData().getRestaurantCookItemList()) {
                                MenuResponse.Dish newDish = new MenuResponse.Dish();
                                newDish.setId(dish.getId());
                                newDish.setName(dish.getName());
                                newDish.setImgUrl(dish.getImgUrl());
                                newDish.setPrice(dish.getPrice());
                                dishList.add(newDish);
                            }
                            getMvpView().prepareDishForAutocomplete(dishList);
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
    public void createMeal(Long dailyMenuId, MealResponse.MealDetails mealDetails) {

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .addMeal(dailyMenuId, mealDetails)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<DailyMenuResponse>() {
                    @Override
                    public void accept(DailyMenuResponse response) throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().hideLoading();
                            getMvpView().back();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().hideLoading();
                    }
                }));
    }

    @Override
    public void loadMeal(long mealId) {
        getCompositeDisposable().add(getDataManager()
                .getMealApiCall(mealId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MealResponse>() {
                    @Override
                    public void accept(MealResponse mealResponse) throws Exception {
                        if(mealResponse!=null && mealResponse.getData()!=null){
                            getMvpView().updateMeal(mealResponse.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void updateMeal(long mealId, MealResponse.MealDetails mealDetails) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .updateMeal(mealId, mealDetails)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<DailyMenuResponse>() {
                    @Override
                    public void accept(DailyMenuResponse response) throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().hideLoading();
                            getMvpView().back();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().hideLoading();
                    }
                }));
    }
}
