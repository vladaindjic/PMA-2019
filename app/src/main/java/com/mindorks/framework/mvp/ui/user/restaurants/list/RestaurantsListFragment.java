package com.mindorks.framework.mvp.ui.user.restaurants.list;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;
import com.mindorks.framework.mvp.ui.user.subscrptions.SubscriptionActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsListFragment extends BaseFragment implements
        RestaurantsListMvpView, UserRestaurantsCallback {


    private static final String TAG = "RestaurantsListFragment";

    @Inject
    RestaurantsListMvpPresenter<RestaurantsListMvpView> mPresenter;

    @Inject
    RestaurantsListAdapter mRestaurantsListAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.restaurants_list_recyclerview)
    RecyclerView mRecyclerView;

    private static final int LOCATION_REQUEST_CODE = 333;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;

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
            mRestaurantsListAdapter.setBasePresenterForImageUrlProviding((BasePresenter) mPresenter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // vi3 prebaceno onResume
        fetchLastLocation();
    }

    @Override
    protected void setUp(View view) {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRestaurantsListAdapter);

        // klijent za lokaciju
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getBaseActivity());
//        // vi3 prebaceno onResume
//        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getBaseActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getBaseActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                return;
            }
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getBaseActivity(),
                            currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    System.out.println(currentLocation.getLatitude() + " " + currentLocation.getLongitude());
                    findAndPrepareProperView(currentLocation.getLatitude(), currentLocation.getLongitude());
                } else {
                    Toast.makeText(getBaseActivity(), "No Location recorded", Toast.LENGTH_SHORT).show();
                    findAndPrepareProperView(null, null);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                } else {
                    Toast.makeText(getBaseActivity(), "Location permission missing", Toast.LENGTH_SHORT).show();
                    // korisnik ne dozvoljava da pristupimo lokaciji
                    findAndPrepareProperView(null, null);
                }
                break;
        }
    }

    private void findAndPrepareProperView(Double latitude, Double longitude) {
        BaseActivity parent = getBaseActivity();
        if (parent instanceof UserRestaurantsActivity) {
            mPresenter.onViewPrepared(RestaurantsListMvpPresenter.PREPARE_ALL_RESTAURANTS,
                    latitude, longitude);
        } else if (parent instanceof SubscriptionActivity) {
            mPresenter.onViewPrepared(RestaurantsListMvpPresenter.PREPARE_MY_RESTAURANTS,
                    latitude, longitude);
        }
    }


    @Override
    public void updateRestaurantsList(List<RestaurantsResponse.Restaurant> restaurants) {
        mRestaurantsListAdapter.addItems(restaurants);
    }


    @Override
    public void onsEmptyViewRetryButtonClick() {

    }

    @Override
    public void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant) {
        UserRestaurantsCallback userRestaurantsActivity = (UserRestaurantsCallback) getActivity();
        if (userRestaurantsActivity != null) {
            userRestaurantsActivity.openRestaurantDetailsActivity(restaurant);
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                =
                (ConnectivityManager) getBaseActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
