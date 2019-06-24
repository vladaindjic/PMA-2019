package com.mindorks.framework.mvp.ui.user.restaurants.map;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface RestaurantsMapMvpPresenter<V extends RestaurantsMapMvpView>
        extends MvpPresenter<V> {
    public void onViewPrepared(Double latitude, Double longitude);
}
