package com.mindorks.framework.mvp.ui.settings;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface SettingsMvpPresenter <V extends SettingsMvpView> extends MvpPresenter<V> {
    public void onViewPrepared();
}