package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantDailyMenuMvpPresenter<V extends ManagerRestaurantDailyMenuMvpView> extends MvpPresenter<V> {
    public void onViewPrepared(Long restaurantId);

}
