package com.mindorks.framework.mvp.ui.restaurant.user.list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsListFragment extends BaseFragment implements
        RestaurantsListMvpView, RestaurantsListAdapter.Callback {


    private static final String TAG = "RestaurantsListFragment";

    @Inject
    RestaurantsListMvpPresenter<RestaurantsListMvpView> mPresenter;

    @Inject
    RestaurantsListAdapter mRestaurantsListAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.restaurants_list_recyclerview)
    RecyclerView mRecyclerView;


    public static RestaurantsListFragment newInstance() {
        Bundle args = new Bundle();
        RestaurantsListFragment fragment = new RestaurantsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants_list, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mRestaurantsListAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRestaurantsListAdapter);

        mPresenter.onViewPrepared();

    }


    // FIXME vi3: sta koji kurac sa ovime da radim
    @Override
    public void onRestaurantsEmptyViewRetryClick() {

    }

    @Override
    public void updateRestaurantsList(List<RestaurantsResponse.Restaurant> restaurants) {
        mRestaurantsListAdapter.addItems(restaurants);
    }
}
