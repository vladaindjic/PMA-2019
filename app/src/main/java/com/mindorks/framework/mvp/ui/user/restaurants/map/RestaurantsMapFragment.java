package com.mindorks.framework.mvp.ui.user.restaurants.map;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
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

    private GoogleMap mMap = null;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST_CODE = 101;
    private boolean myLocationPermission = false;
    private Marker myLocationMarker;

    private List<RestaurantsResponse.Restaurant> restaurantList;
    private Map<String, RestaurantsResponse.Restaurant> stringRestaurantMap;
    private Map<String, Drawable> restaurantsDrawablesMap;
    private List<Marker> markerList = new ArrayList<>();


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
    public void onResume() {
        super.onResume();
        // vi3 prebaceno onResume
        // dobavljamo lokaciju
        System.out.println("VI3 uvek je null: " + mMap);
        if (mMap != null && myLocationPermission) {
            fetchLastLocation();
        }

    }

    @Override
    protected void setUp(View view) {
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(mRestaurantsListAdapter);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getBaseActivity());
//        // vi3 prebaceno onResume
//        // dobavljamo lokaciju
//        fetchLastLocation();

        // moramo u isto vreme da dobavimo lokaciju, kao i restorano
        // prvo probamo dobavljanje lokacije
        // nakon toga dobavljamo restorane i iscrtavamo
        //mPresenter.onViewPrepared();
    }

    private void fetchLastLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getBaseActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                return;
            }
        }

        myLocationPermission = true;
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getBaseActivity(),
                            currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    System.out.println(currentLocation.getLatitude() + " " + currentLocation.getLongitude());
                    mPresenter.onViewPrepared(currentLocation.getLatitude(),
                            currentLocation.getLongitude());
                } else {
                    Toast.makeText(getBaseActivity(), "No Location recorded", Toast.LENGTH_SHORT).show();
                    mPresenter.onViewPrepared(null, null);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        System.out.println("****************************************** POZOVI SE PLS " + requestCode);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    // moramo prvo postaviti sve sto treba na mapu, pa onda ucitavati
                    System.out.println("VI3: ovo je mMap " + mMap + ", a ja sam ti dozvolio " +
                            "permisiju");
                    myLocalGoogleMapSetUp();
                } else {
                    Toast.makeText(getBaseActivity(), "Location permission missing", Toast.LENGTH_SHORT).show();
                    // korisnik ne dozvoljava da pristupimo lokaciji
                    mPresenter.onViewPrepared(null, null);
                }
                break;
        }
    }
    //  37.4219983 -122.084


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println("VI3: ovo je mMap " + mMap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getBaseActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                System.out.println("VI3: ovo je mMap " + mMap + " a ja ispadam");
                return;
            }
        }

        myLocalGoogleMapSetUp();

    }

    @SuppressLint("MissingPermission")
    private void myLocalGoogleMapSetUp() {
        if (mMap == null) {
            System.out.println("VI3 MAPA: ne valja, Vladimire, dzaba... :(");
        }

        mMap.setMyLocationEnabled(true);

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (stringRestaurantMap.containsKey(marker.getId())) {
                    openRestaurantDetailsActivity(stringRestaurantMap.get(marker.getId()));
                }
            }
        });
        myLocationPermission = true;
        fetchLastLocation();
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
        // cistimo sve prethodne markere ako ih ima
        for (Marker m : this.markerList) {
            m.remove();
        }
        this.markerList.clear();
        this.restaurantList.clear();
        this.restaurantsDrawablesMap.clear();
        this.stringRestaurantMap.clear();

        this.restaurantList.addAll(restaurants);

        this.showRestaurantsOnMap();
    }


    private LatLng showCurrentLocationMarker() {
        final LatLng latLng = new LatLng(currentLocation.getLatitude(),
                currentLocation.getLongitude());
        if (myLocationMarker != null) {
            myLocationMarker.remove();
        }

        Glide.with(getActivity()).load(
                ((BasePresenter) mPresenter).getDataManager().getCurrentUserProfilePicUrl())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        myLocationMarker = mMap.addMarker(new MarkerOptions()
                                .position(latLng));

                        // postavljamo ikonicu, nazv restorana i adresu
                        BitmapDescriptor bitmapDescriptor =
                                BitmapDescriptorFactory.fromBitmap(createCustomMarker(getContext(), resource));
                        // zakomentarisati, ako ne treba slika da se prikaze
                        myLocationMarker.setIcon(bitmapDescriptor);
                        myLocationMarker.setTitle(((BasePresenter) mPresenter).getDataManager().getCurrentUserName());
                        myLocationMarker.setSnippet(getString(R.string.here_you_are));
                        // cuvamo drawable
                        restaurantsDrawablesMap.put(myLocationMarker.getId(), resource);
                        // vezemo restoran za marker
                        // stringRestaurantMap.put(marker.getId(), restaurant);
                    }

                    // TODO vi3: ovde handlati ako ne moze da se ucita slika korisnika
                });

        return latLng;
    }

    private void showRestaurantsOnMap() {
        if (this.restaurantList == null) {
            if (currentLocation != null) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(showCurrentLocationMarker());
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                mMap.moveCamera(cu);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
            }

            return;
        } else {
            List<LatLng> restaurantsLocations = new ArrayList<>();
            for (final RestaurantsResponse.Restaurant restaurant : this.restaurantList) {
                if (restaurant.getLatitude() == null || restaurant.getLongitude() == null) {
                    continue;
                }

                final LatLng restaurantLocation = new LatLng(restaurant.getLatitude(),
                        restaurant.getLongitude());
                // pamtimo lokaciju
                restaurantsLocations.add(restaurantLocation);
                String urlKogaJebes =
                        ((BasePresenter) mPresenter).getImageUrlFor(BasePresenter.ENTITY_RESTAURANT, restaurant.getImageUrl());
                Glide.with(getActivity()).load(((BasePresenter) mPresenter).getImageUrlFor(BasePresenter.ENTITY_RESTAURANT, restaurant.getImageUrl()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(new SimpleTarget<Drawable>() {

                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                System.out.println("VI3 mora ovde: uspesno");
                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .position(restaurantLocation));
                                // kesiramo u listu
                                markerList.add(marker);
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

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                // prikaz podrazumevane slike za restoran
                                Glide.with(getActivity()).load(BasePresenter.ENTITY_RESTAURANT_IMAGE_URL)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .into(new SimpleTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                System.out.println("VI3 mora ovde: neuspesno");
                                                Marker marker = mMap.addMarker(new MarkerOptions()
                                                        .position(restaurantLocation));
                                                // kesiramo u listu
                                                markerList.add(marker);
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
                        });

            }

            if (restaurantsLocations.size() > 0) {
                //LatLngBound will cover all your marker on Google Maps
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng location : restaurantsLocations) {
                    builder.include(location); //Taking Point A (First LatLng)
                }

                if (currentLocation != null) {
                    builder.include(showCurrentLocationMarker());
                }

                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                mMap.moveCamera(cu);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
            }

        }


        // FIXME vi3: sta raditi ako ne dobavljamo slicice
    }

    @Override
    public void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant) {
        UserRestaurantsActivity userRestaurantsActivity = (UserRestaurantsActivity) getActivity();
        if (userRestaurantsActivity != null) {
            userRestaurantsActivity.openRestaurantDetailsActivity(restaurant);
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onsEmptyViewRetryButtonClick() {

    }

}
