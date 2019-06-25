package com.mindorks.framework.mvp.ui.user.restaurant.promotions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantPromotionsFragment extends BaseFragment implements
        UserRestaurantPromotionsMvpView, UserRestaurantPromotionsCallback {


    private static final String TAG = "UserRestaurantPromotionsFragment";

    @Inject
    UserRestaurantPromotionsMvpPresenter<UserRestaurantPromotionsMvpView> mPresenter;

    //Inject Adapter
    @Inject
    UserRestaurantPromotionsAdapter mUserRestaurantPromotionsAdapter;

    //Inject LayoutManager
    @Inject
    LinearLayoutManager mLayoutManager;

    //Bind View
    @BindView(R.id.restaurant_promotions_view)
    RecyclerView mRecyclerView;


    public UserRestaurantPromotionsFragment() {
        // Required empty public constructor
    }

    public static UserRestaurantPromotionsFragment newInstance() {
        Bundle args = new Bundle();
        UserRestaurantPromotionsFragment fragment = new UserRestaurantPromotionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_restaurant_promotions, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mUserRestaurantPromotionsAdapter.setmCallback(this);
            mUserRestaurantPromotionsAdapter.setBasePresenterForImageUrlProviding((BasePresenter)mPresenter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // vi3 prebaceno onResume
        Long restaurantId = getBaseActivity().getIntent().getLongExtra("restaurantId", 0L);
        mPresenter.onViewPrepared(restaurantId);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(getBaseActivity() != null) {
                // vi3 prebaceno onResume
                Long restaurantId = getBaseActivity().getIntent().getLongExtra("restaurantId", 0L);
                mPresenter.onViewPrepared(restaurantId);
            }
        }
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mUserRestaurantPromotionsAdapter);

//        // vi3 prebaceno onResume
//        Long restaurantId = getBaseActivity().getIntent().getLongExtra("restaurantId", 0L);
//        mPresenter.onViewPrepared(restaurantId);

    }
    @Override
    public void updateRestaurantPromotionsList(List<RestaurantPromotionsResponse.Promotion> promotions) {
        mUserRestaurantPromotionsAdapter.addItems(promotions);
    }


    @Override
    public void onRestaurantsEmptyViewRetryClick() {

    }

    @Override
    public void openPromotionDetailsActivity(RestaurantPromotionsResponse.Promotion promotion) {
        UserRestaurantActivity userRestaurantActivity = (UserRestaurantActivity)getActivity();
        if (userRestaurantActivity != null) {
            userRestaurantActivity.openPromotionDetailsActivity(promotion);
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }
}
