package com.mindorks.framework.mvp.ui.user.restaurants;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mindorks.framework.mvp.ui.user.restaurants.grid.RestaurantsGridFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListFragment;

public class UserRestaurantsPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public UserRestaurantsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return RestaurantsListFragment.newInstance();
            case 1:
                return RestaurantsGridFragment.newInstance();
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
