package com.mindorks.framework.mvp.ui.manager.restaurant.promotions;

import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface ManagerRestaurantPromotionsMvpView extends MvpView {

    void updateRestaurantPromotionsList(List<RestaurantPromotionsResponse.Promotion> promotions);

}
