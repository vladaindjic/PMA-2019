package com.mindorks.framework.mvp.ui.manager.restaurant.promotions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantPromotionsFragment extends BaseFragment implements
        ManagerRestaurantPromotionsMvpView, ManagerRestaurantPromotionsCallback {


    private static final String TAG = "ManagerRestaurantPromotionsFragment";

    @Inject
    ManagerRestaurantPromotionsMvpPresenter<ManagerRestaurantPromotionsMvpView> mPresenter;

    //Inject Adapter
    @Inject
    ManagerRestaurantPromotionsAdapter mManagerRestaurantPromotionsAdapter;

    //Inject LayoutManager
    @Inject
    LinearLayoutManager mLayoutManager;

    //Bind View
    @BindView(R.id.manager_restaurant_promotions_view)
    RecyclerView mRecyclerView;


    public static ManagerRestaurantPromotionsFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantPromotionsFragment fragment = new ManagerRestaurantPromotionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerRestaurantPromotionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_restaurant_promotions, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mManagerRestaurantPromotionsAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mManagerRestaurantPromotionsAdapter);

        // TODO vi3: dobaljanje restorana za koji je menadzer zaduzen
        mPresenter.onViewPrepared();
    }

    @Override
    public void onRestaurantsEmptyViewRetryClick() {

    }

    @Override
    public void openPromotionDetailsActivity(RestaurantPromotionsResponse.Promotion promotion) {
        // TODO vi3: ovde ima smisla implementirati logiku za editovanje ili brisanje
    }

    @Override
    public void updateRestaurantPromotionsList(List<RestaurantPromotionsResponse.Promotion> promotions) {
        mManagerRestaurantPromotionsAdapter.addItems(promotions);

    }
}
