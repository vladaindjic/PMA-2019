package com.mindorks.framework.mvp.ui.filter;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface RestaurantFilterMvpPresenter<V extends RestaurantFilterMvpView> extends MvpPresenter<V> {
    public void onViewPrepared();

    public void readUserFilter();

}
