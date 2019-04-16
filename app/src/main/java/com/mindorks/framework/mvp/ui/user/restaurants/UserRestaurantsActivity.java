package com.mindorks.framework.mvp.ui.user.restaurants;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.about.AboutFragment;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.custom.RoundedImageView;
import com.mindorks.framework.mvp.ui.filter.RestaurantFilterActivity;
import com.mindorks.framework.mvp.ui.login.LoginActivity;
import com.mindorks.framework.mvp.ui.main.MainActivity;
import com.mindorks.framework.mvp.ui.notification.NotificationFragment;
import com.mindorks.framework.mvp.ui.settings.SettingsFragment;
import com.mindorks.framework.mvp.ui.user.details.UserDetailsFragment;
import com.mindorks.framework.mvp.ui.user.preferences.UserPreferencesFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;
import com.mindorks.framework.mvp.ui.user.subscrptions.SubscriptionActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRestaurantsActivity extends BaseActivity implements UserRestaurantsMvpView, UserRestaurantsCallback {

    @Inject
    UserRestaurantsMvpPresenter<UserRestaurantsMvpView> mPresenter;

    @Inject
    UserRestaurantsPagerAdapter mPagerAdapter;

    @BindView(R.id.user_restaurants_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.user_restaurants_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_view)
    DrawerLayout mDrawer;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private RoundedImageView mProfileImageView;

    private TextView mNameTextView;

    private TextView mEmailTextView;

    private ActionBarDrawerToggle mDrawerToggle;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserRestaurantsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_restaurants);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(UserRestaurantsActivity.this);

        setUp();
    }

    // add toolbar
    @Override
    protected void setUp() {

        setSupportActionBar(mToolbar);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        mPagerAdapter.setCount(3);

        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.list)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.grid)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.map)));

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
//        setupCardContainerView();
//        mPresenter.onViewInitialized();

    }

    void setupNavMenu() {
        View headerLayout = mNavigationView.getHeaderView(0);
        mProfileImageView = (RoundedImageView) headerLayout.findViewById(R.id.iv_profile_pic);
        mNameTextView = (TextView) headerLayout.findViewById(R.id.tv_name);
        mEmailTextView = (TextView) headerLayout.findViewById(R.id.tv_email);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawer.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.nav_item_about:
                                mPresenter.onDrawerOptionAboutClick();
                                return true;
//                            case R.id.nav_item_rate_us:
////                                mPresenter.onDrawerRateUsClick();
//                                return true;
                            case R.id.nav_item_feed:
                                mPresenter.onDrawerMyNotificationsClick();
                                return true;
                            case R.id.nav_item_restaurants:
                                mPresenter.onDrawerRestaurantsClick();
                                return true;
                            case R.id.nav_item_my_restaurants:
                                mPresenter.onDrawerMyRestaurantsClick();
                                return true;
                            case R.id.nav_item_my_profile:
                                mPresenter.onDrawerMyProfileClick();
                                return true;
                            case R.id.nav_item_settings:
                                mPresenter.onDrawerMySettingsClick();
                                return true;
                            case R.id.nav_item_logout:
                                mPresenter.onDrawerOptionLogoutClick();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.user_restaurants_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_restaurants_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(UserRestaurantsActivity.this, "this is queryyyyyyyyy: " + query,
                        Toast.LENGTH_SHORT).show();
                searchItem.collapseActionView();
                // TODO vi3: posalji upit i reloaduj restorane
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // sta je korisnik selektovao
        switch (item.getItemId()) {
            case R.id.action_restaurants_filter:

                Toast.makeText(this, "Share option selected", Toast.LENGTH_SHORT).show();
                Intent intent = RestaurantFilterActivity.getStartIntent(UserRestaurantsActivity.this);
                startActivity(intent);
                return true;
        }
        // ako nismo nista izabrali, pozovemo super metodu
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant) {
        // take care about plural
        Intent intent = UserRestaurantActivity.getStartIntent(UserRestaurantsActivity.this);
        intent.putExtra("restaurantId", restaurant.getId());
        startActivity(intent);
        //finish();

    }

    @Override
    public void onsEmptyViewRetryButtonClick() {

    }

    @Override
    public void closeNavigationDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void showAboutFragment() {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.user_cl_root_view, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
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


    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
        System.out.println("OVDI SAM");
        finish();
    }


    @Override
    public void updateUserName(String currentUserName) {
        mNameTextView.setText(currentUserName);
    }

    @Override
    public void updateUserEmail(String currentUserEmail) {
        mEmailTextView.setText(currentUserEmail);
    }

    @Override
    public void updateUserProfilePic(String currentUserProfilePicUrl) {
        Glide.with(this)
                .load(currentUserProfilePicUrl)
                .into(mProfileImageView);
        //load profile pic url into ANImageView
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
    }


    @Override
    public void openNotificationsActivity() {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.user_cl_root_view, NotificationFragment.newInstance(), NotificationFragment.TAG)
                .commit();
    }

    @Override
    public void openRestaurantsActivity() {
        Intent intent = MainActivity.getStartIntent(this);
        startActivity(intent);

    }

    @Override
    public void openMyRestaurantsActivity() {
        Intent intent = SubscriptionActivity.getStartIntent(this);
        startActivity(intent);

    }

    @Override
    public void openMyProfileActivity() {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(R.id.user_cl_root_view, UserDetailsFragment.newInstance(), UserDetailsFragment.TAG)
                .commit();
    }

    @Override
    public void openSettingsActivity() {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(R.id.user_cl_root_view, SettingsFragment.newInstance(), SettingsFragment.TAG)
                .commit();
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .disallowAddToBackStack()
//                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//                .add(R.id.user_cl_root_view, UserPreferencesFragment.newInstance(),
//                        SettingsFragment.TAG)
//                .commit();
        // TODO vi3: ovde otvoriti fragment
    }
}
