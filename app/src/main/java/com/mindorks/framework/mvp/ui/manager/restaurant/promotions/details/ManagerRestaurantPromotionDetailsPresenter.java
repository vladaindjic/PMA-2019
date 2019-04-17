package com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsMvpPresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ManagerRestaurantPromotionDetailsPresenter<V extends ManagerRestaurantPromotionDetailsMvpView>
        extends BasePresenter<V> implements ManagerRestaurantPromotionDetailsMvpPresenter<V> {

    private static final String TAG = "ManagerRestaurantPromotionDetailsPresenter";

    @Inject
    public ManagerRestaurantPromotionDetailsPresenter(DataManager dataManager,
                                                      SchedulerProvider schedulerProvider,
                                                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

}
