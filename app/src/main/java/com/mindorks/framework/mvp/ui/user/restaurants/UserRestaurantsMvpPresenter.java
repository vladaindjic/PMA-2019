package com.mindorks.framework.mvp.ui.user.restaurants;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserRestaurantsMvpPresenter<V extends UserRestaurantsMvpView> extends MvpPresenter<V> {
    void onDrawerOptionAboutClick();

    void onDrawerOptionLogoutClick();

    void onNavMenuCreated();

    void onDrawerMyNotificationsClick();

    void onDrawerRestaurantsClick();

    void onDrawerMyRestaurantsClick();

    void onDrawerMyProfileClick();

    void onDrawerMySettingsClick();

    void checkIfSubscriptionOptionChange();
}
