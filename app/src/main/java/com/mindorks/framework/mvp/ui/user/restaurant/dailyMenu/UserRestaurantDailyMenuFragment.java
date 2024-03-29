package com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishTypeListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;

import java.text.SimpleDateFormat;
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


//    @BindView(R.id.txt_user_daily_menu_name)
//    TextView txtName;
//    @BindView(R.id.user_daily_menu_start_time_txt)
//    TextView txtStart;
//    @BindView(R.id.user_daily_menu_end_time_txt)
//    TextView txtEnd;


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
            if (getBaseActivity() != null) {
                // vi3 prebaceno onResume
//                Long restaurantId = getBaseActivity().getIntent().getLongExtra("restaurantId", 0L);
//                mPresenter.onViewPrepared(restaurantId);
            }
        }
    }

    @Override
    protected void setUp(View view) {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMealListAdapter);
//        // vi3 prebaceno onResume
//        Long restaurantId = getBaseActivity().getIntent().getLongExtra("restaurantId", 0L);
//        mPresenter.onViewPrepared(restaurantId);
    }

    @Override
    public void openMealActivity(DailyMenuResponse.Meal meal) {
        UserRestaurantActivity userRestaurantActivity = (UserRestaurantActivity) getActivity();
        if (userRestaurantActivity != null) {
            userRestaurantActivity.openMealActivity(meal);
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateDailyMenu(DailyMenuResponse.DailyMenu dailyMenu) {
//        if (dailyMenu.getName() != null) {
//            txtName.setText(dailyMenu.getName());
//        }
//
//        SimpleDateFormat timeFormat = new SimpleDateFormat(getString(R.string.time_format));
//
//        if (dailyMenu.getStartTime() != null) {
//            txtStart.setText(timeFormat.format(dailyMenu.getStartTime()));
//        }
//
//        if (dailyMenu.getEndTime() != null) {
//            txtEnd.setText(timeFormat.format(dailyMenu.getEndTime()));
//        }

        if (dailyMenu.getMeals() != null) {
            mMealListAdapter.addItems(dailyMenu.getMeals());
        }
    }

    @Override
    public void onDailyMenuEmptyViewRetryClick() {

    }
}
