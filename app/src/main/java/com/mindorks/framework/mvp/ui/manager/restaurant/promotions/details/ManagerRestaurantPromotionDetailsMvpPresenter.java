package com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details;

import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantPromotionDetailsMvpPresenter<V extends ManagerRestaurantPromotionDetailsMvpView> extends MvpPresenter<V>  {

    public void loadData(int promotionId);
    public void savePromotion(PromotionDetailsResponse.Promotion promotion);
}
