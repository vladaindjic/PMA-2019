package com.mindorks.framework.mvp.ui.user.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends BaseFragment implements
        UserDetailsMvpView {

    public static final String TAG = "UserDetailsFragment";

    @Inject
    UserDetailsMvpPresenter<UserDetailsMvpView> mPresenter;

    @BindView(R.id.user_details_img)
    ImageView imageView;

    @BindView(R.id.user_details_name)
    TextView txtViewName;

    @BindView(R.id.user_details_lastname)
    TextView txtViewLastname;

    @BindView(R.id.user_details_username)
    TextView txtViewUsername;

    @BindView(R.id.user_details_email)
    TextView txtViewEmail;

    @Inject
    LinearLayoutManager mLayoutManager;

    private UserDetailsResponse.UserDetails details;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public static UserDetailsFragment newInstance() {
        Bundle args = new Bundle();
        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

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
        Bundle bundle = getArguments();
        Long Id = bundle.getLong("Id");

        //mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mPresenter.onViewPrepared(Id);
    }

    @Override
    public void updateDetails(final UserDetailsResponse.UserDetails details) {
        this.details = details;

        // azuriranje polja koja se prikazuje
        txtViewName.setText(details.getName());
        txtViewLastname.setText(details.getLastname());
        txtViewEmail.setText(details.getEmail());
        txtViewUsername.setText(details.getUsername());


        if (details.getImageUrl() != null) {
            Glide.with(this)
                    .load(details.getImageUrl())
                    .into(imageView);
        }
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
