package com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

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
}
