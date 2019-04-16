package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserRestaurantRatingMvpPresenter<V extends UserRestaurantRatingMvpView> extends MvpPresenter<V> {

    public final static int PREPARE_RESTAURANT_RATING = 0;
    public final static int PREPARE_DISH_RATING = 1;

    public void onViewPrepared(int prepareType ,Long restaurantId);
}
