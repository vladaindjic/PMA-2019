package com.mindorks.framework.mvp.ui.user.subscrptions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsMvpView;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscriptionActivity extends BaseActivity implements SubscriptionMvpView, UserRestaurantsCallback {

    @Inject
    SubscriptionMvpPresenter<SubscriptionMvpView> mPresenter;


    RestaurantsListFragment restaurantsListFragment;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SubscriptionActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SubscriptionActivity.this);

        // ovo se eventualno moze injektova
        restaurantsListFragment = RestaurantsListFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.my_restaurants_fragment_container, restaurantsListFragment, null)
                .commit();


        setUp();
    }

    @Override
    protected void setUp() {
    }

    @Override
    public void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant) {
        // take care about plural
        Intent intent = UserRestaurantActivity.getStartIntent(SubscriptionActivity.this);
        intent.putExtra("restaurantId", restaurant.getId());
        startActivity(intent);
        //finish();
    }

    @Override
    public void onsEmptyViewRetryButtonClick() {

    }
}
