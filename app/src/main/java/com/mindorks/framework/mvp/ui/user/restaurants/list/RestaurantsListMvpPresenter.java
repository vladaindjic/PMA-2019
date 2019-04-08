package com.mindorks.framework.mvp.ui.user.restaurants.list;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface RestaurantsListMvpPresenter<V extends RestaurantsListMvpView>
        extends MvpPresenter<V> {
    public void onViewPrepared();
}
