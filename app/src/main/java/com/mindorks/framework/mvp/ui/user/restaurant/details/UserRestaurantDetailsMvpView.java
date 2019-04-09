package com.mindorks.framework.mvp.ui.user.restaurant.details;

import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserRestaurantDetailsMvpView extends MvpView {

    void updateRestaurantDetails(RestaurantDetailsResponse.RestaurantDetails restaurantDetails);

}
