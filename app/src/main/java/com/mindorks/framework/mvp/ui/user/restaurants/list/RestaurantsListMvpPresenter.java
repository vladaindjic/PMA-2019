package com.mindorks.framework.mvp.ui.user.restaurants.list;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface RestaurantsListMvpPresenter<V extends RestaurantsListMvpView>
        extends MvpPresenter<V> {

    public final static int PREPARE_ALL_RESTAURANTS = 0;
    public final static int PREPARE_MY_RESTAURANTS = 1;

    public void onViewPrepared(int whatToPrepare);

}
