package com.mindorks.framework.mvp.ui.user.restaurant.promotions;

import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;

public interface UserRestaurantPromotionsCallback {

    void onRestaurantsEmptyViewRetryClick();
    void openPromotionDetailsActivity(RestaurantPromotionsResponse.Promotion promotion);
}
