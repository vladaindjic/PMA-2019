package com.mindorks.framework.mvp.ui.user.restaurant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.ratings.UserRestaurantRatingFragment;

public class UserRestaurantPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public UserRestaurantPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO trenutno: ovde ubaciti fragmente
        switch (position) {
            case 0:
                return UserRestaurantDetailsFragment.newInstance();
            case 1:
                return UserRestaurantRatingFragment.newInstance();
            case 2:
                return UserRestaurantPromotionsFragment.newInstance();
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
