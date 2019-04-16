package com.mindorks.framework.mvp.ui.user.meal;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserMealPresenter<V extends UserMealMvpView> extends BasePresenter<V>
        implements UserMealMvpPresenter<V> {
    private static final String TAG = "UserDishPresenter";

    @Inject
    public UserMealPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long mealId) {
        getMvpView().showLoading();

        MealResponse.MealDetails mealDetails = new MealResponse.MealDetails();
        mealDetails.setId(1L);
        mealDetails.setName("Obilan obrok");
        mealDetails.setStartTime(new Date());
        mealDetails.setEndTime(new Date());
        mealDetails.setPrice(333.33);
        mealDetails.setDescription("Jedan izuzetno obilan obrok. Kad se nakrkas, samo cepas cega " +
                "posle");


        // jela
        List<MenuResponse.Dish> jela = new ArrayList<>();
        final String imgUrl = "https://www.themediterraneandish" +
                ".com/wp-content/uploads/2017/01/Shakshuka-Recipe-The-Mediterranean-Dish-102.jpg";
        final double price = 333.33;
        jela.add(new MenuResponse.Dish(1L, "Supa", imgUrl, price));
        jela.add(new MenuResponse.Dish(2L, "Piletina", imgUrl, price));
        jela.add(new MenuResponse.Dish(3L, "salata", imgUrl, price));

        mealDetails.setDishList(jela);


        // nutritivne vrednosti
        List<DishDetailsResponse.NutritiveValue> nutritiveValues = new ArrayList<>();
        nutritiveValues.add(new DishDetailsResponse.NutritiveValue(1L, "kalorijska vrednost",
                1000.0, "kcal"));
        nutritiveValues.add(new DishDetailsResponse.NutritiveValue(1L, "proteini",
                33.0, "g"));
        nutritiveValues.add(new DishDetailsResponse.NutritiveValue(1L, "masti",
                23.0, "g"));

        mealDetails.setNutritiveValues(nutritiveValues);


        getMvpView().updateMealDetails(mealDetails);
        getMvpView().hideLoading();

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
