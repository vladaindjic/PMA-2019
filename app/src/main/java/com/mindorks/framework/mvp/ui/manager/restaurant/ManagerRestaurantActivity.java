package com.mindorks.framework.mvp.ui.manager.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.login.LoginActivity;
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

    @BindView(R.id.drawer_view_manager)
    DrawerLayout mDrawer;

    @BindView(R.id.navigation_view_manager)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;


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

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        mPresenter.onNavMenuCreated();

    }

    void setupNavMenu() {
//        View headerLayout = mNavigationView.getHeaderView(0);
//        mProfileImageView = (RoundedImageView) headerLayout.findViewById(R.id.iv_profile_pic);
//        mNameTextView = (TextView) headerLayout.findViewById(R.id.tv_name);
//        mEmailTextView = (TextView) headerLayout.findViewById(R.id.tv_email);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.nav_manager_item_logout:
                                mPresenter.onDrawerOptionLogoutClick();
                                return true;
                            default:
                                return false;
                        }
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
    public void openDailyMenuDetailsActivity(Long dailyMenuId, Long mealId) {
        Intent intent = ManagerDailyMenuDetailsActivity.getStartIntent(this);
        intent.putExtra("menuId", dailyMenuId);
        intent.putExtra("mealId", mealId);
        startActivity(intent);
    }

    public void openDishDetailsActivity(MenuResponse.Dish dish) {
        Intent intent = ManagerDishDetailsActivity.getStartIntent(this);
        intent.putExtra("dishId", dish.getId());
        startActivity(intent);
        //finish();
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
        System.out.println("OVDI SAM");
        finish();
    }

    @Override
    public void closeNavigationDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void lockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}
