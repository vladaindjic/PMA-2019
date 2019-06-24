package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.details;

import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

import java.util.List;

public interface ManagerDailyMenuDetailsMvpPresenter<V extends ManagerDailyMenuDetailsMvpView> extends MvpPresenter<V> {

    void prepareDishForAutocomplete();

    void createMeal(Long dailyMenuId, MealResponse.MealDetails mealDetails);

    void loadMeal(long mealId);

    void updateMeal(long mealId, MealResponse.MealDetails mealDetails);
}
