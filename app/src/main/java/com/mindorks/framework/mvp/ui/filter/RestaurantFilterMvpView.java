package com.mindorks.framework.mvp.ui.filter;

import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface RestaurantFilterMvpView extends MvpView {

    void updateRestaurantFilterOptions(List<RestaurantFilterResponse.RestaurantFilter.RestaurantFilterOptions> restaurantFilterOptions);

    void updateKitchenOptions(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> kitchenOptions);

    void updateDistance(int distance);
}
