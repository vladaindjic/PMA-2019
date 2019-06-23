package com.mindorks.framework.mvp.ui.manager.restaurant;

import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface ManagerRestaurantMvpView extends MvpView {

    public void openPromotionDetailsActivity(Long promotionId);

    public void openDailyMenuDetailsActivity(Long dailyMenuId,Long mealId);

    public void openLoginActivity();

    public void closeNavigationDrawer();

    public void lockDrawer();

    public void unlockDrawer();

}
