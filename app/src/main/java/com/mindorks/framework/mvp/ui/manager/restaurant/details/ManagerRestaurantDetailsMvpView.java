package com.mindorks.framework.mvp.ui.manager.restaurant.details;

import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface ManagerRestaurantDetailsMvpView extends MvpView {

    void updateRestaurantDetails(RestaurantDetailsResponse.RestaurantDetails restaurantDetails);

    void prepareKitchensForAutocomplete(List<RestaurantDetailsResponse.Kitchen> kitchenList);

    byte[] getImgBytes();
}
