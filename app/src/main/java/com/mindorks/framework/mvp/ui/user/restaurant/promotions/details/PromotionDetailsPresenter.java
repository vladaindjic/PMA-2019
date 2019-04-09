package com.mindorks.framework.mvp.ui.user.restaurant.promotions.details;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PromotionDetailsPresenter<V extends PromotionDetailsMvpView> extends BasePresenter<V> implements PromotionDetailsMvpPresenter<V> {


    private static final String TAG = "PromotionDetailsPresenter";

    @Inject
    public PromotionDetailsPresenter(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
