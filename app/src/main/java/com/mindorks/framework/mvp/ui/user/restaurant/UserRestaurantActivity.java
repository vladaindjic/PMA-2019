package com.mindorks.framework.mvp.ui.user.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.details.PromotionDetailsActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRestaurantActivity extends BaseActivity implements UserRestaurantMvpView {

    @Inject
    UserRestaurantMvpPresenter<UserRestaurantMvpView> mPresenter;

    @Inject
    UserRestaurantPagerAdapter mPagerAdapter;

    @BindView(R.id.user_restaurant_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.user_restaurant_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_restaurant);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(UserRestaurantActivity.this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        mPagerAdapter.setCount(3);

        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.details)));
        //Dodan tab sa utiscima
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.rating));
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

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserRestaurantActivity.class);
        return intent;
    }

    @Override
    public void openRestaurantDetailsActivity(RestaurantPromotionsResponse.Promotion promotion) {
        Intent intent = PromotionDetailsActivity.getStartIntent(UserRestaurantActivity.this);
        startActivity(intent);
        finish();
    }
}
