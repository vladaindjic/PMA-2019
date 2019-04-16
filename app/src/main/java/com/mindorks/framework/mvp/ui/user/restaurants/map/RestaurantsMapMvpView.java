package com.mindorks.framework.mvp.ui.user.restaurants.map;

import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface RestaurantsMapMvpView extends MvpView {

    void updateRestaurantsList(List<RestaurantsResponse.Restaurant> restaurants);
}
