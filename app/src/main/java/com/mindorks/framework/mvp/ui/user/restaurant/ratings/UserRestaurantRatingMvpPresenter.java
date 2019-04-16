package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserRestaurantRatingMvpPresenter<V extends UserRestaurantRatingMvpView> extends MvpPresenter<V> {


    public void onViewPrepared(Long restaurantId);
}
