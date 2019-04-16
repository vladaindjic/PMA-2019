package com.mindorks.framework.mvp.ui.user.dish;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserDishPresenter<V extends UserDishMvpView> extends BasePresenter<V>
        implements UserDishMvpPresenter<V> {
    private static final String TAG = "UserDishPresenter";

    @Inject
    public UserDishPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

}
