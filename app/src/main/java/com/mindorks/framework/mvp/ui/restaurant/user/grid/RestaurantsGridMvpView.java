package com.mindorks.framework.mvp.ui.restaurant.user.grid;

import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface RestaurantsGridMvpView extends MvpView {
    void updateRestaurantsList(List<RestaurantsResponse.Restaurant> restaurants);
}
