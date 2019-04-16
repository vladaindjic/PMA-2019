package com.mindorks.framework.mvp.ui.user.dish;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.ratings.UserRestaurantRatingFragment;

public class UserDishPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public UserDishPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO trenutno: ovde ubaciti fragmente
        switch (position) {
            case 0:
                return UserDishDetailsFragment.newInstance();
            case 1:
                return UserRestaurantRatingFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }
}
