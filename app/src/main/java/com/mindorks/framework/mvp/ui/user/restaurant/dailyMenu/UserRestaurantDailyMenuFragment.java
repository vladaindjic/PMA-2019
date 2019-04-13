package com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu;


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
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishTypeListAdapter;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantDailyMenuFragment extends BaseFragment implements UserRestaurantDailyMenuMvpView, MealListAdapter.MealListItemCallback {

    private static final String TAG = "UserRestaurantDailyMenuFragment";


    @Inject
    UserRestaurantDailyMenuMvpPresenter<UserRestaurantDailyMenuMvpView> mPresenter;

    @Inject
    MealListAdapter mMealListAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.user_daily_menu_meal_list_recyclerview)
    RecyclerView mRecyclerView;

    public UserRestaurantDailyMenuFragment() {
        // Required empty public constructor
    }

    public static UserRestaurantDailyMenuFragment newInstance() {
        Bundle args = new Bundle();
        UserRestaurantDailyMenuFragment fragment = new UserRestaurantDailyMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_restaurant_daily_menu, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mMealListAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        Long restaurantId = bundle.getLong("restaurantId");

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMealListAdapter);

        mPresenter.onViewPrepared(restaurantId);
    }

    @Override
    public void openMealActivity(DailyMenuResponse.Meal meal) {

    }

    @Override
    public void updateDailyMenu(DailyMenuResponse.DailyMenu dailyMenu) {
    }
}
