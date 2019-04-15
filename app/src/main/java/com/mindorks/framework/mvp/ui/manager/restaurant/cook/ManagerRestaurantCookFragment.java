package com.mindorks.framework.mvp.ui.manager.restaurant.cook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.details.ManagerRestaurantDetailsFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.details.ManagerRestaurantDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.details.ManagerRestaurantDetailsMvpView;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantCookFragment extends BaseFragment implements
        ManagerRestaurantCookMvpView {

    @Inject
    ManagerRestaurantCookMvpPresenter<ManagerRestaurantCookMvpView> mPresenter;

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
    }

    @Override
    public void updateRestaurantCook() {

    }
}
