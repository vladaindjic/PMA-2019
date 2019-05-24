package com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserRestaurantDailyMenuPresenter<V extends UserRestaurantDailyMenuMvpView> extends BasePresenter<V>
        implements UserRestaurantDailyMenuMvpPresenter<V> {


    private static final String TAG = "UserRestaurantDailyMenuPresenter";

    @Inject
    public UserRestaurantDailyMenuPresenter(DataManager dataManager,
                                            SchedulerProvider schedulerProvider,
                                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long restaurantId) {
        getMvpView().showLoading();


        DailyMenuResponse.DailyMenu dailyMenu = new DailyMenuResponse.DailyMenu();
        dailyMenu.setId(1L);
        dailyMenu.setName("Dnevni meniji nasega restorana");
        dailyMenu.setStartTime(new Date());
        dailyMenu.setEndTime(new Date());

        DailyMenuResponse.Meal m1 = new DailyMenuResponse.Meal();
        m1.setId(1L);
        m1.setName("Prvi obrok");
        m1.setPrice(300.00);
        m1.setDescription("Ovo je jedan odlican obrok. Supica, glavno jelo, salata");
        
        DailyMenuResponse.Meal m2 = new DailyMenuResponse.Meal();
        m2.setId(2L);
        m2.setName("Drugi obrok");
        m2.setPrice(200.00);
        m2.setDescription("Ovo je jos bolji obrok. Supica, glavno jelo, prilog, salata");
        
        DailyMenuResponse.Meal m3 = new DailyMenuResponse.Meal();
        m3.setId(3L);
        m3.setName("Treci obrok");
        m3.setPrice(350.00);
        m3.setDescription("Ovo je najbolji obrok. Supica, glavno jelo, prilog, salata");

        DailyMenuResponse.Meal m4 = new DailyMenuResponse.Meal();
        m4.setId(4L);
        m4.setName("Cetvrti obrok");
        m4.setPrice(350.00);
        m4.setDescription("Ovo je najbolji obrok. Supica, glavno jelo, prilog, prilog, prilog, " +
                "prilog, prilog, prilog, prilog,prilog, prilog,prilog, prilog,prilog, prilog,prilog, prilog," +
                " salata");


        DailyMenuResponse.Meal m5 = new DailyMenuResponse.Meal();
        m5.setId(5L);
        m5.setName("Peti obrok");
        m5.setPrice(350.00);
        m5.setDescription("Ovo je najbolji obrok. Supica, glavno jelo, prilog, salata");


        List<DailyMenuResponse.Meal> meals = new ArrayList<>();
        meals.add(m1);
        meals.add(m2);
        meals.add(m3);
        meals.add(m4);
        meals.add(m5);

        dailyMenu.setMeals(meals);

        getMvpView().updateDailyMenu(dailyMenu);

        getMvpView().hideLoading();

        // TODO SREDITI vi3: REST API call
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
