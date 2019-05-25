package com.mindorks.framework.mvp.ui.manager.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.cook.ManagerRestaurantCookItemListAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.details.ManagerDailyMenuDetailsActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.dish.ManagerDishDetailsActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details.ManagerRestaurantPromotionDetailsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerRestaurantActivity extends BaseActivity implements ManagerRestaurantMvpView, ManagerRestaurantCookItemListAdapter.ManagerCookItemListCallback {

    @Inject
    ManagerRestaurantMvpPresenter<ManagerRestaurantMvpView> mPresenter;

    @Inject
    ManagerRestaurantPagerAdapter mPagerAdapter;

    @BindView(R.id.manager_restaurant_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.manager_restaurant_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ManagerRestaurantActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_restaurant);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ManagerRestaurantActivity.this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

        setSupportActionBar(mToolbar);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        mPagerAdapter.setCount(5);

        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.details)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.cook)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.restaurant_menu)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.daily_menu)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.promotions)));

        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void openDishActivity(MenuResponse.Dish dish) {
        // TODO vi3: ovo cemo recimo iskoristiti za uklanjanje jela
    }

    @Override
    public void openPromotionDetailsActivity(Long promotionId) {
        Intent intent = ManagerRestaurantPromotionDetailsActivity.getStartIntent(this);
        intent.putExtra("promotionId", promotionId);
        startActivity(intent);
//        finish();
    }

    @Override
    public void openDailyMenuDetailsActivity(int dailyMenuId) {
        Intent intent = ManagerDailyMenuDetailsActivity.getStartIntent(this);
        intent.putExtra("menuId", dailyMenuId);
        startActivity(intent);
    }

    public void openDishDetailsActivity(MenuResponse.Dish dish) {
        Intent intent = ManagerDishDetailsActivity.getStartIntent(this);
        intent.putExtra("dishId", dish.getId());
        startActivity(intent);
        //finish();
    }
}
