package com.mindorks.framework.mvp.ui.manager.restaurant.promotions;

import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;

public interface ManagerRestaurantPromotionsCallback {

    void onRestaurantsEmptyViewRetryClick();
    void openPromotionDetailsActivity(RestaurantPromotionsResponse.Promotion promotion);
    //TODO Milan: Da li slati citavu promociju ili samo id.
    void deletePromotion(Long promotionId);
}
