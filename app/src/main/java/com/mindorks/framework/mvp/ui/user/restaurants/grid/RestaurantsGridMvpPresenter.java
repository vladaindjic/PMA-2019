package com.mindorks.framework.mvp.ui.user.restaurants.grid;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface RestaurantsGridMvpPresenter<V extends RestaurantsGridMvpView>
        extends MvpPresenter<V> {
    public void onViewPrepared();
}
