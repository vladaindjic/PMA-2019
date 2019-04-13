package com.mindorks.framework.mvp.ui.user.meal;

import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserMealMvpView extends MvpView {
    void updateMealDetails(MealResponse.MealDetails mealDetails);

}
