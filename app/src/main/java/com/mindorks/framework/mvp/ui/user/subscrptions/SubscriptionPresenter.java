package com.mindorks.framework.mvp.ui.user.subscrptions;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.LogoutResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SubscriptionPresenter<V extends SubscriptionMvpView> extends BasePresenter<V>
        implements SubscriptionMvpPresenter<V> {

    private static final String TAG = "SubscriptionPresenter";

    @Inject
    public SubscriptionPresenter(DataManager dataManager,
                                 SchedulerProvider schedulerProvider,
                                 CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void onViewPrepared() {

    }
}
