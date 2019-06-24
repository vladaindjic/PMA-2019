package com.mindorks.framework.mvp.ui.user.restaurants.utils;

import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.utils.OnRetryButtonClickCallback;

public interface UserRestaurantsCallback extends OnRetryButtonClickCallback {

    void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant);

}
