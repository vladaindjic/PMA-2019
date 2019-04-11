package com.mindorks.framework.mvp.ui.user.restaurant.menu;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserRestaurantMenuPresenter<V extends UserRestaurantMenuMvpView> extends BasePresenter<V>
        implements UserRestaurantMenuMvpPresenter<V> {

    private static final String TAG = "UserRestaurantMenuPresenter";


    @Inject
    public UserRestaurantMenuPresenter(DataManager dataManager,
                                       SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
