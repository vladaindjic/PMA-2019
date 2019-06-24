package com.mindorks.framework.mvp.ui.manager.restaurant.cook;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantCookMvpPresenter<V extends ManagerRestaurantCookMvpView>
        extends MvpPresenter<V> {

    public void onViewPrepared();

    void deleteDish(Long id);
}
