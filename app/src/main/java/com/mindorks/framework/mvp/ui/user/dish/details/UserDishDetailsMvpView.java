package com.mindorks.framework.mvp.ui.user.dish.details;

import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserDishDetailsMvpView extends MvpView {

    void updateDishDetails(DishDetailsResponse.DishDetails dishDetails);

}
