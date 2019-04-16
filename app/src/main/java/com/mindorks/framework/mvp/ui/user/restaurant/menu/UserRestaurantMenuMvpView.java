package com.mindorks.framework.mvp.ui.user.restaurant.menu;

import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserRestaurantMenuMvpView extends MvpView {

    void updateMenu(MenuResponse.Menu menu);

}
