package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.details;

import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface ManagerDailyMenuDetailsMvpView extends MvpView {


    public void prepareDishForAutocomplete(List<MenuResponse.Dish> dishList);
    public void updateMeal(MealResponse.MealDetails meal);
    public void back();
}
