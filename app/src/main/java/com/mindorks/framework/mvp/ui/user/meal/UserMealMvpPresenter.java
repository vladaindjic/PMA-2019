package com.mindorks.framework.mvp.ui.user.meal;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserMealMvpPresenter<V extends UserMealMvpView> extends MvpPresenter<V> {

    public void onViewPrepared(Long mealId);

}
