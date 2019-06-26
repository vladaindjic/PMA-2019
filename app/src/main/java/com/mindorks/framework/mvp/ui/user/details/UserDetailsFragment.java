package com.mindorks.framework.mvp.ui.user.details;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

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

    UserDetailsResponse.UserDetails oldUser;


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

    @BindView(R.id.edit_user_details_update_btn)
    Button btnUpdateUserDetails;

    @BindView(R.id.change_password_update_btn)
    Button btnChangePassword;

    @Inject
    LinearLayoutManager mLayoutManager;

    private UserDetailsResponse.UserDetails details;
    private String mCameraFileName;


    public UserDetailsFragment() {
        // Required empty public constructor
        this.oldUser = new UserDetailsResponse.UserDetails();
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
    public void onResume() {
        super.onResume();
        // vi3 prebaceno onResume
        Bundle bundle = getArguments();
        Long Id = bundle.getLong("Id");
        mPresenter.onViewPrepared(Id);

    }

    @Override
    protected void setUp(View view) {
        // vi3 prebaceno onResume
//        Bundle bundle = getArguments();
//        Long Id = bundle.getLong("Id");
//        mPresenter.onViewPrepared(Id);

        btnUpdateUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserDetailsUpdateActivity();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserDetailsPasswordActivity();
            }
        });
    }

    @Override
    public void updateDetails(final UserDetailsResponse.UserDetails details) {
        this.details = details;
        //copy new data
        oldUser.setEmail(details.getEmail());
        oldUser.setName(details.getName());
        oldUser.setLastname(details.getLastname());
        oldUser.setUsername(details.getUsername());
        oldUser.setImageUrl(details.getImageUrl());

        // azuriranje polja koja se prikazuje
        txtViewName.setText(details.getName());
        txtViewLastname.setText(details.getLastname());
        txtViewEmail.setText(details.getEmail());
        txtViewUsername.setText(details.getUsername());

        String imgUrl = ((BasePresenter) mPresenter).getDataManager().getCurrentUserProfilePicUrl();
        System.out.println("updateDetails " + imgUrl);
        Glide.with(this)
                .load(imgUrl)
                // kako bismo izbegli kesiranje
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);

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

    private void openUserDetailsUpdateActivity() {
        ((UserRestaurantsActivity)getBaseActivity()).openUserDetailsUpdateActivity(this.details);
    }

    private void openUserDetailsPasswordActivity() {
        ((UserRestaurantsActivity)getBaseActivity()).openUserDetailsPasswordActivity(this.details);
    }


}
