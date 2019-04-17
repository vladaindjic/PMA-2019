package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface ManagerRestaurantMenuMvpView extends MvpView {

    void updateMenu(MenuResponse.Menu menu);
    void updateAllRestaurantDishes(List<MenuResponse.Dish> dishList);
}
