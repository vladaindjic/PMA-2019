package com.mindorks.framework.mvp.ui.user.restaurant.details;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserRestaurantDetailsPresenter<V extends UserRestaurantDetailsMvpView> extends BasePresenter<V>
        implements UserRestaurantDetailsMvpPresenter<V> {

    private static final String TAG = "UserRestaurantDetailsPresenter";

    @Inject
    public UserRestaurantDetailsPresenter(DataManager dataManager,
                                          SchedulerProvider schedulerProvider,
                                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
