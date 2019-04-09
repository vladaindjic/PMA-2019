package com.mindorks.framework.mvp.ui.user.restaurant.promotions.details;

import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface PromotionDetailsMvpView extends MvpView {

    void updatePromotion(PromotionDetailsResponse.Promotion promotion);
}
