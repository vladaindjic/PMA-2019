package com.mindorks.framework.mvp.ui.user.restaurant.promotions;

import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface UserRestaurantPromotionsMvpView extends MvpView {

    void updateRestaurantPromotionsList(List<RestaurantPromotionsResponse.Promotion> promotions);

}
