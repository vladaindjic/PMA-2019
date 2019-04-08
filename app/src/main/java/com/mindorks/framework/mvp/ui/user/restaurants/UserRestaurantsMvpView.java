package com.mindorks.framework.mvp.ui.user.restaurants;

import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserRestaurantsMvpView extends MvpView {

    public void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant);
}
