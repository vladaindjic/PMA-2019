package com.mindorks.framework.mvp.ui.user.dish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsFragment;
import com.mindorks.framework.mvp.ui.user.dish.util.AddNutritiveValuesCallback;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishTypeListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.details.PromotionDetailsActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDishActivity extends BaseActivity implements UserDishMvpView {

    public static String LOG_TAG = "JEBE";

    @Inject
    UserDishMvpPresenter<UserDishMvpView> mPresenter;

    @Inject
    UserDishPagerAdapter mPagerAdapter;

    @BindView(R.id.user_dish_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.user_dish_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    AddNutritiveValuesCallback addNutritiveValuesCallback = null;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserDishActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dish);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(UserDishActivity.this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        mPagerAdapter.setCount(2);

        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.details)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.rating)));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UserDishDetailsFragment.GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                if(this.addNutritiveValuesCallback != null) {
                    this.addNutritiveValuesCallback.addNutritiveValues();
                }
            }
        }
    }

    public AddNutritiveValuesCallback getAddNutritiveValuesCallback() {
        return addNutritiveValuesCallback;
    }

    public void setAddNutritiveValuesCallback(AddNutritiveValuesCallback addNutritiveValuesCallback) {
        this.addNutritiveValuesCallback = addNutritiveValuesCallback;
    }
}
