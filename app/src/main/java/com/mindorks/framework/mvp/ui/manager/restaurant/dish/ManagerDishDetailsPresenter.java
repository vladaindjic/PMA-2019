package com.mindorks.framework.mvp.ui.manager.restaurant.dish;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ManagerDishDetailsPresenter<V extends ManagerDishDetailsMvpView> extends BasePresenter<V>
        implements ManagerDishDetailsMvpPresenter<V> {

    private static final String TAG = "ManagerDishDetailsPresenter";

    @Inject
    public ManagerDishDetailsPresenter(DataManager dataManager,
                                       SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long restaurantId) {
        getMvpView().showLoading();
        DishDetailsResponse.DishDetails dishDetails = new DishDetailsResponse.DishDetails();
        dishDetails.setId(1L);
        dishDetails.setName("Lazanje");
        dishDetails.setPrice(33.3);
        dishDetails.setDescription("Najbolje");
        dishDetails.setImageUrl("https://www.tasteofhome.com/wp-content/uploads/2017/10/Italian-Hot-Dish_EXPS_HCK19_31288_B08_24_2b-2-696x696.jpg");
        dishDetails.setKitchen(new DishDetailsResponse.Kitchen(1L, "Italijanska"));
        List<DishDetailsResponse.NutritiveValue> nutritiveValues = new ArrayList<>();
        nutritiveValues.add(new DishDetailsResponse.NutritiveValue(1L, "kalorijska vrednost",
                1000.0, "kcal"));
        nutritiveValues.add(new DishDetailsResponse.NutritiveValue(1L, "proteini",
                33.0, "g"));
        nutritiveValues.add(new DishDetailsResponse.NutritiveValue(1L, "masti",
                23.0, "g"));
        dishDetails.setNutritiveValues(nutritiveValues);
        getMvpView().updateDishDetails(dishDetails);
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