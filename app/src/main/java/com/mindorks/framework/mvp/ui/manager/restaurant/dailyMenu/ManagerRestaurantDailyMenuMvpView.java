package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu;

import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface ManagerRestaurantDailyMenuMvpView extends MvpView {
    public void updateDailyMenu(DailyMenuResponse.DailyMenu dailyMenu);
}
