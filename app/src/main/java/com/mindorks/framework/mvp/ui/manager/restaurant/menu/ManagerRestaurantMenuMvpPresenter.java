package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantMenuMvpPresenter<V extends ManagerRestaurantMenuMvpView> extends MvpPresenter<V> {

    public void onViewPrepared(Long restaurantId);
}
