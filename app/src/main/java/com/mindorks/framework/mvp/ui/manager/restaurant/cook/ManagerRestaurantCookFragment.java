package com.mindorks.framework.mvp.ui.manager.restaurant.cook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantCookFragment extends BaseFragment implements
        ManagerRestaurantCookMvpView {

    @Inject
    ManagerRestaurantCookMvpPresenter<ManagerRestaurantCookMvpView> mPresenter;

    public static final String TAG = "ManagerRestaurantCookFragment";

    @Inject
    ManagerRestaurantCookItemListAdapter mManagerRestaurantCookItemListAdapter;

    @BindView(R.id.item_cook_item_list_view)
    RecyclerView mManagerRestaurantCookView;

    @Inject
    LinearLayoutManager mLayoutManager;

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
            // mKitchensAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        mPresenter.onViewPrepared(1L);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mManagerRestaurantCookView.setLayoutManager(mLayoutManager);
        mManagerRestaurantCookView.setItemAnimator(new DefaultItemAnimator());
        mManagerRestaurantCookView.setAdapter(mManagerRestaurantCookItemListAdapter);

        System.out.println("POZIVAM ON onVPrepared");

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
}
