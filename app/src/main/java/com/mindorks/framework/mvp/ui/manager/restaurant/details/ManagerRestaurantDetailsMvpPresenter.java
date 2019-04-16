package com.mindorks.framework.mvp.ui.manager.restaurant.details;

import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantDetailsMvpPresenter<V extends ManagerRestaurantDetailsMvpView>
        extends MvpPresenter<V> {

    void onViewPrepared(Long restaurantId);
    void submitRestaurantDetails(RestaurantDetailsResponse.RestaurantDetails restaurantDetails);
}
