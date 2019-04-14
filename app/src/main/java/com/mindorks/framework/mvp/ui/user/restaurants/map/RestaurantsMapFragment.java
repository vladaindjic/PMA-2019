package com.mindorks.framework.mvp.ui.user.restaurants.map;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListMvpView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsMapFragment extends BaseFragment implements
        RestaurantsMapMvpView, OnMapReadyCallback {


    private static final String TAG = "RestaurantsMapFragment";

    @Inject
    RestaurantsMapMvpPresenter<RestaurantsMapMvpView> mPresenter;

    @BindView(R.id.user_restaurants_map)
    MapView mMapView;

    private GoogleMap mMap;


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

            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            mMapView.getMapAsync(this);

        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //When Map Loads Successfully
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                LatLng customMarkerLocationOne = new LatLng(28.583911, 77.319116);
                LatLng customMarkerLocationTwo = new LatLng(28.583078, 77.313744);
                LatLng customMarkerLocationThree = new LatLng(28.580903, 77.317408);
                LatLng customMarkerLocationFour = new LatLng(28.580108, 77.315271);
//                mMap.addMarker(new MarkerOptions().position(customMarkerLocationOne).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(getContext(),R.drawable.login_bg,"Manish")))).setTitle("iPragmatech Solutions Pvt Lmt");
//                mMap.addMarker(new MarkerOptions().position(customMarkerLocationTwo).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(getContext(),R.drawable.login_bg,"Narender")))).setTitle("Hotel Nirulas Noida");
//
//                mMap.addMarker(new MarkerOptions().position(customMarkerLocationThree).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(getContext(),R.drawable.login_bg,"Neha")))).setTitle(
//                                        "Acha Khao Acha Khilao");
//                mMap.addMarker(new MarkerOptions().position(customMarkerLocationFour).
//                        icon(BitmapDescriptorFactory.fromBitmap(
//                                createCustomMarker(getContext(),R.drawable.login_bg,"Nupur")))).setTitle("Subway Sector 16 Noida");

                final Marker m = mMap.addMarker(new MarkerOptions()
                        .position(customMarkerLocationFour)
                        .title("Naslov")
                        .snippet("Opis")
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromBitmap(
                                createCustomMarker(getContext(), R.drawable.login_bg, "Nupur"))));
                //LatLngBound will cover all your marker on Google Maps
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(customMarkerLocationOne); //Taking Point A (First LatLng)
                builder.include(customMarkerLocationThree); //Taking Point B (Second LatLng)
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                mMap.moveCamera(cu);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);


                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        System.out.println("Na sta si kliknu retarde???? " + marker.getTitle());
                    }
                });
            }
        });
    }

    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageResource(resource);
//        TextView txt_name = (TextView) marker.findViewById(R.id.name);
//        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
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
