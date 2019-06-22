package com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details;

import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface ManagerRestaurantPromotionDetailsMvpView extends MvpView {

    public void updatePromotion(PromotionDetailsResponse.Promotion promotion);
    public void back();
}
