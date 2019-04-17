package com.mindorks.framework.mvp.ui.manager.restaurant.cook;

import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface ManagerRestaurantCookMvpView extends MvpView {

//    void updateRestaurantDetails(RestaurantDetailsResponse.RestaurantDetails restaurantDetails);

    void updateRestaurantCook(List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> restaurantCookItemList);
}
