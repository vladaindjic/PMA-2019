package com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu;

import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserRestaurantDailyMenuMvpView extends MvpView {
    public void updateDailyMenu(DailyMenuResponse.DailyMenu dailyMenu);
}
