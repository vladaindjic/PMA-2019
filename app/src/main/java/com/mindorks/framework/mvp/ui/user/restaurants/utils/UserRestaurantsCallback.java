package com.mindorks.framework.mvp.ui.user.restaurants.utils;

import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;

public interface UserRestaurantsCallback {

    void onRestaurantsEmptyViewRetryClick();
    void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant);
}
