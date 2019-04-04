package com.mindorks.framework.mvp.ui.restaurant.user.list;

import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface RestaurantsListMvpView extends MvpView {

    void updateRestaurantsList(List<RestaurantsResponse.Restaurant> restaurants);
}
