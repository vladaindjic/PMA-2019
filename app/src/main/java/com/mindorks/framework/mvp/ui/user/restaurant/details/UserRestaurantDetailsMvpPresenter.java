package com.mindorks.framework.mvp.ui.user.restaurant.details;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserRestaurantDetailsMvpPresenter<V extends UserRestaurantDetailsMvpView>
        extends MvpPresenter<V> {

    public void onViewPrepared(Long restaurantId);

    void subscribeToRestaurant(Long restaurantId);

}
