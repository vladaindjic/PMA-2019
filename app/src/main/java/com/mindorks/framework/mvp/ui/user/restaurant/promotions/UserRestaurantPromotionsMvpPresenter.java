package com.mindorks.framework.mvp.ui.user.restaurant.promotions;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsMvpView;

public interface UserRestaurantPromotionsMvpPresenter<V extends UserRestaurantPromotionsMvpView>
        extends MvpPresenter<V> {
    public void onViewPrepared(Long restaurantId);

}
