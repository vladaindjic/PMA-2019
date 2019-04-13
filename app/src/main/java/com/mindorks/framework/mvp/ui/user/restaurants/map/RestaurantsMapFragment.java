package com.mindorks.framework.mvp.ui.user.restaurants.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListMvpView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsMapFragment extends BaseFragment implements
        RestaurantsMapMvpView {


    private static final String TAG = "RestaurantsMapFragment";

    @Inject
    RestaurantsMapMvpPresenter<RestaurantsMapMvpView> mPresenter;


    public static RestaurantsMapFragment newInstance() {
        Bundle args = new Bundle();
        RestaurantsMapFragment fragment = new RestaurantsMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantsMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurants_map, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
//            mRestaurantsListAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(mRestaurantsListAdapter);

        mPresenter.onViewPrepared();

    }

    @Override
    public void updateRestaurantsList(List<RestaurantsResponse.Restaurant> restaurants) {

    }
}
