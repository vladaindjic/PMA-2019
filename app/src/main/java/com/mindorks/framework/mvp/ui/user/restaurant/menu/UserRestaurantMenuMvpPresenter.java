package com.mindorks.framework.mvp.ui.user.restaurant.menu;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserRestaurantMenuMvpPresenter<V extends UserRestaurantMenuMvpView> extends MvpPresenter<V> {

    public void onViewPrepared(Long restaurantId);
}
