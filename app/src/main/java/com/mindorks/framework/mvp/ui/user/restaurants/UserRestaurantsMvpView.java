package com.mindorks.framework.mvp.ui.user.restaurants;

import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserRestaurantsMvpView extends MvpView {

    public void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant);

    void closeNavigationDrawer();

    void showAboutFragment();

    void openLoginActivity();

    void updateUserName(String currentUserName);

    void updateUserEmail(String currentUserEmail);

    void updateUserProfilePic(String profilePicUrl);

    void lockDrawer();

    void unlockDrawer();

    void openNotificationsActivity();

    void openRestaurantsActivity();

    void openMyRestaurantsActivity();

    void openMyProfileActivity();

    void openSettingsActivity();

    void openUserDetailsUpdateActivity(UserDetailsResponse.UserDetails userDetails);

    void openUserDetailsPasswordActivity(UserDetailsResponse.UserDetails userDetails);

}
