package com.mindorks.framework.mvp.ui.user.restaurants;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserRestaurantsPresenter<V extends UserRestaurantsMvpView> extends BasePresenter<V>
        implements UserRestaurantsMvpPresenter<V> {

    private static final String TAG = "UserRestaurantsPresenter";

    @Inject
    public UserRestaurantsPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


}
