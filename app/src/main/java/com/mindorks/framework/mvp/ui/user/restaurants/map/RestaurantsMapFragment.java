package com.mindorks.framework.mvp.ui.user.restaurants.map;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsMapFragment extends BaseFragment implements
        RestaurantsMapMvpView, OnMapReadyCallback, UserRestaurantsCallback {


    private static final String TAG = "RestaurantsMapFragment";

    @Inject
    RestaurantsMapMvpPresenter<RestaurantsMapMvpView> mPresenter;

    @BindView(R.id.user_restaurants_map)
    MapView mMapView;

    private GoogleMap mMap;

    private List<RestaurantsResponse.Restaurant> restaurantList;
    private Map<String, RestaurantsResponse.Restaurant> stringRestaurantMap;
    private Map<String, Drawable> restaurantsDrawablesMap;


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

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (stringRestaurantMap.containsKey(marker.getId())) {
                    openRestaurantDetailsActivity(stringRestaurantMap.get(marker.getId()));
                }
            }
        });


    }


    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View mContents;

        public CustomInfoWindowAdapter() {
            mContents = getLayoutInflater().inflate(R.layout.map_custom_info_window_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            // da li nam treba default ponasanje???
//            return null;

            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            ImageView imgView = view.findViewById(R.id.map_restaurant_image);
            // ovo nece postaviti sliku, jer je asinhrono ocigledno
            // Glide.with(getBaseActivity()).load(R.drawable.login_bg).into(imgView);

            if (restaurantsDrawablesMap.containsKey(marker.getId())) {
                imgView.setImageDrawable(restaurantsDrawablesMap.get(marker.getId()));
            }

            String title = marker.getTitle();
            TextView txtTitle = view.findViewById(R.id.map_restaurant_title);
            if (title != null) {
                txtTitle.setText(title);
            } else {
                txtTitle.setText("");
            }

            String snippet = marker.getSnippet();
            TextView txtSnippet = view.findViewById(R.id.map_restaurant_snippet);
            if (snippet != null) {
                txtSnippet.setText(snippet);
            } else {
                txtSnippet.setText("");
            }
        }

    }

    public Bitmap createCustomMarker(Context context, Drawable resource) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = marker.findViewById(R.id.user_dp);

        markerImage.setImageDrawable(resource);

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
    public void updateRestaurantsList(final List<RestaurantsResponse.Restaurant> restaurants) {
//        return;
        if (this.restaurantList == null) {
            this.restaurantList = new ArrayList<>();
        }

        if (this.restaurantsDrawablesMap == null) {
            this.restaurantsDrawablesMap = new HashMap<>();
        }

        if (this.stringRestaurantMap == null) {
            this.stringRestaurantMap = new HashMap<>();
        }

        if (restaurants == null) {
            return;
        }

        this.restaurantList.clear();
        this.restaurantsDrawablesMap.clear();
        this.stringRestaurantMap.clear();

        this.restaurantList.addAll(restaurants);

        List<LatLng> restaurantsLocations = new ArrayList<>();
        for (final RestaurantsResponse.Restaurant restaurant : this.restaurantList) {
            if (restaurant.getLatitude() == null || restaurant.getLongitude() == null) {
                continue;
            }
//            restaurantLocation = new LatLng(28.583911, 77.319116);
            final LatLng restaurantLocation = new LatLng(restaurant.getLatitude(),
                    restaurant.getLongitude());
            // pamtimo lokaciju
            restaurantsLocations.add(restaurantLocation);

            Glide.with(getActivity()).load(restaurant.getImageUrl())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(restaurantLocation));

                            // postavljamo ikonicu, nazv restorana i adresu
                            BitmapDescriptor bitmapDescriptor =
                                    BitmapDescriptorFactory.fromBitmap(createCustomMarker(getContext(), resource));
                            // zakomentarisati, ako ne treba slika da se prikaze
                            marker.setIcon(bitmapDescriptor);
                            marker.setTitle(restaurant.getName());
                            if (restaurant.getAddress() != null) {
                                marker.setSnippet(restaurant.getAddress());
                            }
                            // cuvamo drawable
                            restaurantsDrawablesMap.put(marker.getId(), resource);
                            // vezemo restoran za marker
                            stringRestaurantMap.put(marker.getId(), restaurant);
                        }
                    });

        }

        if (restaurantsLocations.size() > 0) {
            //LatLngBound will cover all your marker on Google Maps
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng location : restaurantsLocations) {
                builder.include(location); //Taking Point A (First LatLng)
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
            mMap.moveCamera(cu);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        }

        // FIXME vi3: sta raditi ako ne dobavljamo slicice

    }

    @Override
    public void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant) {
        UserRestaurantsActivity userRestaurantsActivity = (UserRestaurantsActivity)getActivity();
        if (userRestaurantsActivity != null) {
            userRestaurantsActivity.openRestaurantDetailsActivity(restaurant);
        } else {
            Toast.makeText(getContext(), "NASISES MI SE KARINE AKO SE DESIS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onsEmptyViewRetryButtonClick() {

    }

    //    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
//        Canvas canvas = new Canvas();
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        canvas.setBitmap(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        drawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }

//    public Bitmap createCustomMarker(Context context, Drawable resource, String _name) {
//
//        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
//
//        CircleImageView markerImage = marker.findViewById(R.id.user_dp);
//
//        markerImage.setImageDrawable(resource);
//
////        markerImage.
////
////                setImageResource(resource);
////        TextView txt_name = (TextView) marker.findViewById(R.id.name);
////        txt_name.setText(_name);
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
//        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
//        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
//        marker.buildDrawingCache();
//        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        marker.draw(canvas);
//
//        return bitmap;
//    }

// //When Map Loads Successfully
//        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//            @Override
//            public void onMapLoaded() {
//
////                LatLng customMarkerLocationOne = new LatLng(28.583911, 77.319116);
////                LatLng customMarkerLocationTwo = new LatLng(28.583078, 77.313744);
////                LatLng customMarkerLocationThree = new LatLng(28.580903, 77.317408);
////                final LatLng customMarkerLocationFour = new LatLng(28.580108, 77.315271);
////                mMap.addMarker(new MarkerOptions().position(customMarkerLocationOne).
////                        icon(BitmapDescriptorFactory.fromBitmap(
////                                createCustomMarker(getContext(),R.drawable.login_bg,"Manish")))).setTitle("iPragmatech Solutions Pvt Lmt");
////                mMap.addMarker(new MarkerOptions().position(customMarkerLocationTwo).
////                        icon(BitmapDescriptorFactory.fromBitmap(
////                                createCustomMarker(getContext(),R.drawable.login_bg,"Narender")))).setTitle("Hotel Nirulas Noida");
////
////                mMap.addMarker(new MarkerOptions().position(customMarkerLocationThree).
////                        icon(BitmapDescriptorFactory.fromBitmap(
////                                createCustomMarker(getContext(),R.drawable.login_bg,"Neha")))).setTitle(
////                                        "Acha Khao Acha Khilao");
////                mMap.addMarker(new MarkerOptions().position(customMarkerLocationFour).
////                        icon(BitmapDescriptorFactory.fromBitmap(
////                                createCustomMarker(getContext(),R.drawable.login_bg,"Nupur")))).setTitle("Subway Sector 16 Noida");
//
////                final Marker m = mMap.addMarker(new MarkerOptions()
////                        .position(customMarkerLocationFour)
////                        .title("Naslov")
////                        .snippet("Opis")
////                        .anchor(0.5f, 0.5f)
////                        .icon(BitmapDescriptorFactory.fromBitmap(
////                                createCustomMarker(getContext(), R.drawable.login_bg, "Nupur"))));
//
////                Glide.with(getActivity()).load(R.drawable.login_bg)
////                        .into(new SimpleTarget<Drawable>() {
////                            @Override
////                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
////                                mMap.addMarker(new MarkerOptions()
////                                        .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(getContext(), resource, "")))
////                                        .position(customMarkerLocationFour)
////                                        .title("Naslov neki"))
////                                ;
////                            }
////                        });
////
////
//                LatLngBound will cover all your marker on Google Maps
////                LatLngBounds.Builder builder = new LatLngBounds.Builder();
////                builder.include(customMarkerLocationOne); //Taking Point A (First LatLng)
////                builder.include(customMarkerLocationThree); //Taking Point B (Second LatLng)
////                LatLngBounds bounds = builder.build();
////                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
////                mMap.moveCamera(cu);
////                mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
////
//
////                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
////                    @Override
////                    public void onInfoWindowClick(Marker marker) {
////                        System.out.println("Na sta si kliknu retarde???? " + marker.getTitle());
////                    }
////                });
////            }
////        });

}
