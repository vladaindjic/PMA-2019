package com.mindorks.framework.mvp.ui.manager.restaurant.cook;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantCookFragment extends BaseFragment implements
        ManagerRestaurantCookMvpView, ManagerCookItemDeleteCallback {

    @Inject
    ManagerRestaurantCookMvpPresenter<ManagerRestaurantCookMvpView> mPresenter;

    public static final String TAG = "ManagerRestaurantCookFragment";

    @Inject
    ManagerRestaurantCookItemListAdapter mManagerRestaurantCookItemListAdapter;

    @BindView(R.id.item_cook_item_list_view)
    RecyclerView mManagerRestaurantCookView;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.cook_item_add_btn)
    FloatingActionButton addDishBtn;

    public ManagerRestaurantCookFragment() {
        // Required empty public constructor
    }

    public static ManagerRestaurantCookFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantCookFragment fragment = new ManagerRestaurantCookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manager_restaurant_cook, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mManagerRestaurantCookItemListAdapter.setmCallback((ManagerRestaurantActivity)getBaseActivity());
            mManagerRestaurantCookItemListAdapter.setmDeleteCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        mPresenter.onViewPrepared();
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mManagerRestaurantCookView.setLayoutManager(mLayoutManager);
        mManagerRestaurantCookView.setItemAnimator(new DefaultItemAnimator());
        mManagerRestaurantCookView.setAdapter(mManagerRestaurantCookItemListAdapter);

        System.out.println("POZIVAM ON onVPrepared");

        addDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmptyDishDetailsActivity();
            }
        });

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }


    @Override
    public void updateRestaurantCook(List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> restaurantCookItemList) {

        mManagerRestaurantCookItemListAdapter.addItems(restaurantCookItemList);
    }

    public void openEmptyDishDetailsActivity() {
        ManagerRestaurantActivity managerRestaurantActivity = (ManagerRestaurantActivity)getActivity();
        if (managerRestaurantActivity != null) {
            MenuResponse.Dish dish = new MenuResponse.Dish();
            dish.setId(-1L);
            managerRestaurantActivity.openDishDetailsActivity(dish);
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteDish(Long id) {
        Toast.makeText(getContext(),"Id: "+id,Toast.LENGTH_SHORT).show();
        mPresenter.deleteDish(id);
    }
}
