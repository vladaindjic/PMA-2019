package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantMenuMvpPresenter<V extends ManagerRestaurantMenuMvpView> extends MvpPresenter<V> {

    void onViewPrepared();
    void getAllRestaurantDishes();
     void submitMenu(MenuResponse.Menu menu);
}
