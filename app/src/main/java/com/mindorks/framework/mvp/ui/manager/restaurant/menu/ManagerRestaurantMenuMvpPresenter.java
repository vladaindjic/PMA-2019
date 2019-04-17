package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantMenuMvpPresenter<V extends ManagerRestaurantMenuMvpView> extends MvpPresenter<V> {

    void onViewPrepared(Long restaurantId);
    void getAllRestaurantDishes(Long restaurantId);

}
