package com.mindorks.framework.mvp.ui.user.restaurant;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserRestaurantPresenter<V extends UserRestaurantMvpView> extends BasePresenter<V>
        implements UserRestaurantMvpPresenter<V> {
    private static final String TAG = "UserRestaurantPresenter";

    @Inject
    public UserRestaurantPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

}
