package com.mindorks.framework.mvp.ui.user.restaurant.promotions;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserRestaurantPromotionsPresenter<V extends UserRestaurantPromotionsMvpView> extends BasePresenter<V>
        implements UserRestaurantPromotionsMvpPresenter<V> {

    private static final String TAG = "UserRestaurantPromotionsPresenter";

    @Inject
    public UserRestaurantPromotionsPresenter(DataManager dataManager,
                                             SchedulerProvider schedulerProvider,
                                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
