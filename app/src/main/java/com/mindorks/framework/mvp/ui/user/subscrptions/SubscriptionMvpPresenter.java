package com.mindorks.framework.mvp.ui.user.subscrptions;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface SubscriptionMvpPresenter<V extends SubscriptionMvpView> extends MvpPresenter<V> {
    void onViewPrepared();
}
