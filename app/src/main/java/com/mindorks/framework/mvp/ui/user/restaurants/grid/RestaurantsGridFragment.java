package com.mindorks.framework.mvp.ui.user.restaurants.grid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
public class RestaurantsGridFragment extends BaseFragment implements
        RestaurantsGridMvpView, RestaurantsGridAdapter.Callback {

    private static final String TAG = "RestaurantsGridFragment";

    @Inject
    RestaurantsGridMvpPresenter<RestaurantsGridMvpView> mPresenter;

    @Inject
    RestaurantsGridAdapter mRestaurantsGridAdapter;

    @Inject
    GridLayoutManager mLayoutManager;

    @BindView(R.id.restaurants_grid_recyclerview)
    RecyclerView mRecyclerView;

    public static RestaurantsGridFragment newInstance() {
        Bundle args = new Bundle();
        RestaurantsGridFragment fragment = new RestaurantsGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantsGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants_grid, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mRestaurantsGridAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRestaurantsGridAdapter);

        mPresenter.onViewPrepared();
    }

    @Override
    public void updateRestaurantsList(List<RestaurantsResponse.Restaurant> restaurants) {
        mRestaurantsGridAdapter.addItems(restaurants);
    }

    // FIXME vi3: sta koji kurac sa ovime da radim
    @Override
    public void onRestaurantsEmptyViewRetryClick() {

    }
}
