package com.mindorks.framework.mvp.ui.user.restaurant;

import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserRestaurantMvpView extends MvpView {

    void openPromotionDetailsActivity(RestaurantPromotionsResponse.Promotion promotion);
}
