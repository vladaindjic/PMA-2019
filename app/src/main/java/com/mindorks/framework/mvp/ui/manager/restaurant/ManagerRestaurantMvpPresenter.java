package com.mindorks.framework.mvp.ui.manager.restaurant;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantMvpPresenter<V extends ManagerRestaurantMvpView> extends MvpPresenter<V> {

    void onNavMenuCreated();
    void onDrawerOptionLogoutClick();

}
