package com.mindorks.framework.mvp.ui.restaurant.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRestaurantsActivity extends BaseActivity implements UserRestaurantsMvpView {

    @Inject
    UserRestaurantsMvpPresenter<UserRestaurantsMvpView> mPresenter;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;


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
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,
//                mDrawer,
//                mToolbar,
//                R.string.open_drawer,
//                R.string.close_drawer) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                hideKeyboard();
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        };
//        mDrawer.addDrawerListener(mDrawerToggle);
//        mDrawerToggle.syncState();
//        setupNavMenu();
//        mPresenter.onNavMenuCreated();
//        setupCardContainerView();
//        mPresenter.onViewInitialized();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.user_restaurants_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_restaurants_search);
        final SearchView searchView = (SearchView)searchItem.getActionView();

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
                return true;
        }
        // ako nismo nista izabrali, pozovemo super metodu
        return super.onOptionsItemSelected(item);
    }


}
