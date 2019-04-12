package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserRestaurantRatingPresenter<V extends UserRestaurantRatingMvpView> extends BasePresenter<V>
        implements UserRestaurantRatingMvpPresenter<V> {

    private static final String TAG = "UserDishRatingPresenter";

    @Inject
    public UserRestaurantRatingPresenter(DataManager dataManager,
                                         SchedulerProvider schedulerProvider,
                                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
