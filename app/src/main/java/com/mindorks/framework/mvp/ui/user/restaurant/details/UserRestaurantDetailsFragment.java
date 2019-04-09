package com.mindorks.framework.mvp.ui.user.restaurant.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantDetailsFragment extends BaseFragment implements
        UserRestaurantDetailsMvpView {

    private static final String TAG = "UserRestaurantDetailsFragment";

    @Inject
    UserRestaurantDetailsMvpPresenter<UserRestaurantDetailsMvpView> mPresenter;

    private RestaurantDetailsResponse.RestaurantDetails restaurantDetails;

    public static UserRestaurantDetailsFragment newInstance() {
        Bundle args = new Bundle();
        UserRestaurantDetailsFragment fragment = new UserRestaurantDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public UserRestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_restaurant_details, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            // mRestaurantsListAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        Long restaurantId = bundle.getLong("restaurantId");
        mPresenter.onViewPrepared(restaurantId);
    }

    @Override
    public void updateRestaurantDetails(RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }
}
