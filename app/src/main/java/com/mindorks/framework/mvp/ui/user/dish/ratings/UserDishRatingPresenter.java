package com.mindorks.framework.mvp.ui.user.dish.ratings;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserDishRatingPresenter<V extends UserDishRatingMvpView> extends BasePresenter<V>
        implements UserDishRatingMvpPresenter<V> {

    private static final String TAG = "UserDishRatingPresenter";

    @Inject
    public UserDishRatingPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
