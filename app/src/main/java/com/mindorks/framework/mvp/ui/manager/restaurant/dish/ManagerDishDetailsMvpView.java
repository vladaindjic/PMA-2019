package com.mindorks.framework.mvp.ui.manager.restaurant.dish;

import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface ManagerDishDetailsMvpView extends MvpView {

    void updateDishDetails(DishDetailsResponse.DishDetails dishDetails);

}