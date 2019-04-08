package com.mindorks.framework.mvp.ui.user.restaurant.promotions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantPromotionsFragment extends BaseFragment implements
        UserRestaurantPromotionsMvpView {


    private static final String TAG = "UserRestaurantPromotionsFragment";

    @Inject
    UserRestaurantPromotionsMvpPresenter<UserRestaurantPromotionsMvpView> mPresenter;

    public static UserRestaurantPromotionsFragment newInstance() {
        Bundle args = new Bundle();
        UserRestaurantPromotionsFragment fragment = new UserRestaurantPromotionsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public UserRestaurantPromotionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_restaurant_promotions, container, false);
    }

    @Override
    protected void setUp(View view) {

    }

}
