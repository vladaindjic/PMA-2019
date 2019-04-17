package com.mindorks.framework.mvp.ui.manager.restaurant.dish;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerDishDetailsMvpPresenter<V extends ManagerDishDetailsMvpView>
        extends MvpPresenter<V> {

    public void onViewPrepared(Long dishId);

}