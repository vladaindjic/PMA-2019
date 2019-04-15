package com.mindorks.framework.mvp.ui.manager.restaurant.details;

import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface ManagerRestaurantDetailsMvpView extends MvpView {

    void updateRestaurantDetails(RestaurantDetailsResponse.RestaurantDetails restaurantDetails);

}
