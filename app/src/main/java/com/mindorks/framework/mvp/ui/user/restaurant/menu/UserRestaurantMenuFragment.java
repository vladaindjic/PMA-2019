package com.mindorks.framework.mvp.ui.user.restaurant.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantMenuFragment extends BaseFragment implements UserRestaurantMenuMvpView {

    private static final String TAG = "UserRestaurantMenuFragment";

    @Inject
    UserRestaurantMenuMvpPresenter<UserRestaurantMenuMvpView> mPresenter;


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
            // mKitchensAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {

    }


}
