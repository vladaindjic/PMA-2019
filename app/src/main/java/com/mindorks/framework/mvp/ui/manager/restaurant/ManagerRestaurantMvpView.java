package com.mindorks.framework.mvp.ui.manager.restaurant;

import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface ManagerRestaurantMvpView extends MvpView {

    public void openPromotionDetailsActivity(int promotionId);
    public void openDailyMenuDetailsActivity(int dailyMenuId);

}
