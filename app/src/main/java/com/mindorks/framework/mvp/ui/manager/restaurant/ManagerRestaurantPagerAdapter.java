package com.mindorks.framework.mvp.ui.manager.restaurant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mindorks.framework.mvp.ui.manager.restaurant.cook.ManagerRestaurantCookFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.ManagerRestaurantDailyMenuFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.details.ManagerRestaurantDetailsFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerRestaurantMenuFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.ratings.UserRestaurantRatingFragment;

public class ManagerRestaurantPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public ManagerRestaurantPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO trenutno: ovde ubaciti fragmente
        switch (position) {
            case 0:
                return ManagerRestaurantDetailsFragment.newInstance();
            case 1:
                return ManagerRestaurantCookFragment.newInstance();
            case 2:
                return ManagerRestaurantMenuFragment.newInstance();
            case 3:
                return ManagerRestaurantDailyMenuFragment.newInstance();
            case 4:
                return ManagerRestaurantPromotionsFragment.newInstance();
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
