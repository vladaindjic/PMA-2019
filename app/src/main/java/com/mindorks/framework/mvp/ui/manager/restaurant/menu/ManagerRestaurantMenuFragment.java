package com.mindorks.framework.mvp.ui.manager.restaurant.menu;


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
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishTypeListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuMvpView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantMenuFragment extends BaseFragment implements ManagerRestaurantMenuMvpView {

    private static final String TAG = "ManagerRestaurantMenuFragment";

    @Inject
    ManagerRestaurantMenuMvpPresenter<ManagerRestaurantMenuMvpView> mPresenter;

    @Inject
    ManagerDishTypeListAdapter mDishTypeListAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.manager_dish_type_list_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.manager_txt_menu_name)
    TextView txtMenuName;

    public static ManagerRestaurantMenuFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantMenuFragment fragment = new ManagerRestaurantMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerRestaurantMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_restaurant_menu, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            // TODO: eventualno callback za RETRY dugme
            // mKitchensAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDishTypeListAdapter);

        // TODO vi3: ovde svakako ide restoran ciji je korisnik manager
        mPresenter.onViewPrepared(1L);
    }

    @Override
    public void updateMenu(MenuResponse.Menu menu) {
        txtMenuName.setText(menu.getName());
        mDishTypeListAdapter.addItems(menu.getDishTypeList());
    }
}
