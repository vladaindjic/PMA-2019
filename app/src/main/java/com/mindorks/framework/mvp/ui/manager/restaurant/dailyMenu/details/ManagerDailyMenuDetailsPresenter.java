package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.details;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

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

        List<MenuResponse.Dish> dishList = new ArrayList<>();
        MenuResponse.Dish d1 = new MenuResponse.Dish();
        d1.setId(1L);
        d1.setName("Supa");
        d1.setPrice(200.00);

        MenuResponse.Dish d2 = new MenuResponse.Dish();
        d2.setId(2L);
        d2.setName("Piletina");
        d2.setPrice(200.00);

        MenuResponse.Dish d3 = new MenuResponse.Dish();
        d3.setId(3L);
        d3.setName("Snenokle");
        d3.setPrice(500.00);

        MenuResponse.Dish d4 = new MenuResponse.Dish();
        d4.setId(4L);
        d4.setName("Supa 2");
        d4.setPrice(200.00);

        dishList.add(d1);
        dishList.add(d2);
        dishList.add(d3);
        dishList.add(d4);

        getMvpView().prepareDishForAutocomplete(dishList);

    }

}
