package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu;


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
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.MealListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuMvpView;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantDailyMenuFragment extends BaseFragment implements ManagerRestaurantDailyMenuMvpView, ManagerMealListAdapter.ManagerMealListItemCallback {


    @Inject
    ManagerRestaurantDailyMenuMvpPresenter<ManagerRestaurantDailyMenuMvpView> mPresenter;

    @Inject
    ManagerMealListAdapter mMealListAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.manager_daily_menu_meal_list_recyclerview)
    RecyclerView mRecyclerView;


    @BindView(R.id.txt_manager_daily_menu_name)
    TextView txtName;
    @BindView(R.id.manager_daily_menu_start_time_txt)
    TextView txtStart;
    @BindView(R.id.manager_daily_menu_end_time_txt)
    TextView txtEnd;

    public static ManagerRestaurantDailyMenuFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantDailyMenuFragment fragment = new ManagerRestaurantDailyMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerRestaurantDailyMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_restaurant_daily_menu, container, false);
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
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMealListAdapter);

        // TODO vi3: ucitati restoran ciji je ulogovani korisnik manager
        mPresenter.onViewPrepared(1L);
    }

    @Override
    public void updateDailyMenu(DailyMenuResponse.DailyMenu dailyMenu) {
        if (dailyMenu.getName() != null) {
            txtName.setText(dailyMenu.getName());
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(getString(R.string.time_format));

        if (dailyMenu.getStartTime() != null) {
            txtStart.setText(timeFormat.format(dailyMenu.getStartTime()));
        }

        if (dailyMenu.getEndTime() != null) {
            txtEnd.setText(timeFormat.format(dailyMenu.getEndTime()));
        }

        if (dailyMenu.getMeals() != null) {
            mMealListAdapter.addItems(dailyMenu.getMeals());
        }
    }

    @Override
    public void openMealActivity(DailyMenuResponse.Meal meal) {
        Toast.makeText(getActivity(),"Otvaram aktivnost izmena",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.add_daily_menu_button)
    public void makeNewDailyMenu(){
        Toast.makeText(getActivity(),"Otvaram aktivnost novi dnevni meni",Toast.LENGTH_SHORT).show();

    }
}
