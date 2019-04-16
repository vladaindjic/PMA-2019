package com.mindorks.framework.mvp.ui.user.restaurant.menu;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserRestaurantMenuPresenter<V extends UserRestaurantMenuMvpView> extends BasePresenter<V>
        implements UserRestaurantMenuMvpPresenter<V> {

    private static final String TAG = "UserRestaurantMenuPresenter";


    @Inject
    public UserRestaurantMenuPresenter(DataManager dataManager,
                                       SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long restaurantId) {
        getMvpView().showLoading();

        MenuResponse.Menu menu = new MenuResponse.Menu(1L, "Moj menu", new ArrayList<MenuResponse.DishType>());

        List<MenuResponse.DishType> dishTypeList = new ArrayList<>();
        List<MenuResponse.Dish> glavnaJela = new ArrayList<>();

        final String imgUrl = "https://www.themediterraneandish" +
                ".com/wp-content/uploads/2017/01/Shakshuka-Recipe-The-Mediterranean-Dish-102.jpg";
        final double price = 333.33;
        glavnaJela.add(new MenuResponse.Dish(1L, "Piletina", imgUrl, price));
        glavnaJela.add(new MenuResponse.Dish(2L, "Prasetina", imgUrl, price));
        glavnaJela.add(new MenuResponse.Dish(3L, "Jagnjetina", imgUrl, price));
        MenuResponse.DishType glavnoJelo = new MenuResponse.DishType(1L, "Glavno jelo", glavnaJela);

        List<MenuResponse.Dish> supe = new ArrayList<>();
        supe.add(new MenuResponse.Dish(1L, "pileca", imgUrl, price));
        supe.add(new MenuResponse.Dish(2L, "teleca", imgUrl, price));
        supe.add(new MenuResponse.Dish(3L, "paradajz", imgUrl, price));
        MenuResponse.DishType supa = new MenuResponse.DishType(1L, "Supa", supe);


        List<MenuResponse.Dish> dezerti = new ArrayList<>();
        dezerti.add(new MenuResponse.Dish(1L, "sladoled", imgUrl, price));
        dezerti.add(new MenuResponse.Dish(2L, "sufle", imgUrl, price));
        dezerti.add(new MenuResponse.Dish(3L, "sladoled kod rajka", imgUrl, price));
        MenuResponse.DishType dezert = new MenuResponse.DishType(1L, "Dezert", dezerti);


        menu.getDishTypeList().add(glavnoJelo);
        menu.getDishTypeList().add(supa);
        menu.getDishTypeList().add(dezert);


        getMvpView().updateMenu(menu);

        getMvpView().hideLoading();

        // TODO vi3: REST API call
//        getCompositeDisposable().add(getDataManager()
//                .getRestaurantDetailsApiCall(restaurantId)
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<RestaurantDetailsResponse>() {
//                    @Override
//                    public void accept(@NonNull RestaurantDetailsResponse response)
//                            throws Exception {
//                        if (response != null && response.getData() != null) {
//                            // TODO vi3: ovo je samo za tesiranje
//                            List<RestaurantDetailsResponse.Kitchen> kitchenList = new ArrayList<>();
//                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(1L, "Kineska"));
//                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(2L,
//                                    "Italijanska"));
//                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(3L, "Srpska"));
//                            response.getData().setKitchens(kitchenList);
//                            getMvpView().updateDishDetails(response.getData());
//                        }
//                        getMvpView().hideLoading();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable)
//                            throws Exception {
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getMvpView().hideLoading();
//
//                        // handle the error here
//                        if (throwable instanceof ANError) {
//                            ANError anError = (ANError) throwable;
//                            handleApiError(anError);
//                        }
//                    }
//                }));
    }
}
