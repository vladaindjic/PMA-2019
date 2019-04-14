package com.mindorks.framework.mvp.ui.notification;

import com.mindorks.framework.mvp.di.PerActivity;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

@PerActivity
public interface NotificationMvpPresenter<V extends NotificationMvpView> extends MvpPresenter<V> {

    void onViewPrepared();
}