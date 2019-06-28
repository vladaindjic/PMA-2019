package com.mindorks.framework.mvp.ui.user.restaurant.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantMenuFragment extends BaseFragment implements UserRestaurantMenuMvpView {

    private static final String TAG = "UserRestaurantMenuFragment";

    @Inject
    UserRestaurantMenuMvpPresenter<UserRestaurantMenuMvpView> mPresenter;

    @Inject
    DishTypeListAdapter mDishTypeListAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.dish_type_list_recyclerview)
    RecyclerView mRecyclerView;

//    @BindView(R.id.txt_menu_name)
//    TextView txtMenuName;


    public UserRestaurantMenuFragment() {
        // Required empty public constructor
    }

    public static UserRestaurantMenuFragment newInstance() {
        Bundle args = new Bundle();
        UserRestaurantMenuFragment fragment = new UserRestaurantMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_restaurant_menu, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            // TODO: eventualno callback za RETRY dugme
            // mKitchensAdapter.setmCallback(this);
            mDishTypeListAdapter.setBasePresenterForImageUrlProviding((BasePresenter) mPresenter);
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
        mRecyclerView.setAdapter(mDishTypeListAdapter);

//        // vi3 prebaceno onResume
//        Long restaurantId = getBaseActivity().getIntent().getLongExtra("restaurantId", 0L);
//        mPresenter.onViewPrepared(restaurantId);

    }

    @Override
    public void updateMenu(MenuResponse.Menu menu) {
//        txtMenuName.setText(menu.getName());
        mDishTypeListAdapter.addItems(menu.getDishTypeList());

    }
}
