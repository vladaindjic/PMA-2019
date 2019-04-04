package com.mindorks.framework.mvp.ui.restaurant.user.grid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.restaurant.user.list.RestaurantsListAdapter;
import com.mindorks.framework.mvp.ui.restaurant.user.list.RestaurantsListMvpPresenter;
import com.mindorks.framework.mvp.ui.restaurant.user.list.RestaurantsListMvpView;

import javax.inject.Inject;

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
    LinearLayoutManager mLayoutManager;

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
            mRestaurantsGridAdapter.setCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {

    }


    // FIXME vi3: sta koji kurac sa ovime da radim
    @Override
    public void onBlogEmptyViewRetryClick() {

    }
}
