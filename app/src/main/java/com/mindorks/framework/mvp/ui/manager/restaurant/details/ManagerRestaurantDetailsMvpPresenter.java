package com.mindorks.framework.mvp.ui.manager.restaurant.details;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantDetailsMvpPresenter<V extends ManagerRestaurantDetailsMvpView>
        extends MvpPresenter<V> {

    public void onViewPrepared(Long restaurantId);

}
