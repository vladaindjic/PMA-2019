package com.mindorks.framework.mvp.ui.user.restaurant.details;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantDetailsFragment extends BaseFragment implements
        UserRestaurantDetailsMvpView {

    private static final String TAG = "UserDishDetailsFragment";

    @Inject
    UserRestaurantDetailsMvpPresenter<UserRestaurantDetailsMvpView> mPresenter;


    @BindView(R.id.user_restaurant_details_img)
    ImageView imageView;

    @BindView(R.id.user_restaurant_details_name)
    TextView txtViewName;

    @BindView(R.id.user_restaurant_details_address)
    TextView txtViewAddress;

    @BindView(R.id.user_restaurant_details_star_button)
    CheckBox checkBoxStar;

    @BindView(R.id.user_dish_details_txt_description_values)
    CheckBox checkBoxDelivery;

    @BindView(R.id.user_dish_details_txt_price_values)
    TextView txtViewPhone;

    @BindView(R.id.user_restaurant_details_txt_work_time_values)
    TextView txtViewWorkTime;

    @BindView(R.id.user_restaurant_details_txt_email_values)
    TextView txtViewEmail;

    @BindView(R.id.btn_how_to_find_us)
    Button btnHowToFindUs;

    // Kitchen
    @Inject
    UserRestaurantDetailsKitchensAdapter mKitchensAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.user_dish_details_nutritive_values_recyclerview)
    RecyclerView mRecyclerView;


    private RestaurantDetailsResponse.RestaurantDetails restaurantDetails;

    public static UserRestaurantDetailsFragment newInstance() {
        Bundle args = new Bundle();
        UserRestaurantDetailsFragment fragment = new UserRestaurantDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public UserRestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_restaurant_details, container, false);

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
        Long restaurantId = getBaseActivity().getIntent().getLongExtra("restaurantId", 0L);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mKitchensAdapter);

        mPresenter.onViewPrepared(restaurantId);
    }

    @Override
    public void updateRestaurantDetails(final RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;

        // azuriranje polja koja se prikazuje
        txtViewName.setText(restaurantDetails.getName());
        txtViewAddress.setText(restaurantDetails.getAddress());

        checkBoxDelivery.setChecked(restaurantDetails.isDelivery());
        checkBoxDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO vi3: intent koji ce otvoriti pozivanje broja
                Toast.makeText(getContext(),
                        "kliknuo sam te",
                        Toast.LENGTH_SHORT).show();
            }
        });


        txtViewPhone.setText(restaurantDetails.getPhone());

        txtViewWorkTime.setText(restaurantDetails.getWorkTime());
        txtViewEmail.setText(restaurantDetails.getEmail());

        btnHowToFindUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (restaurantDetails.getLatitude() == null || restaurantDetails.getLongitude() == null) {
                    Toast.makeText(getContext(),
                            "No location: " + restaurantDetails.getAddress(),
                            Toast.LENGTH_SHORT).show();
                }

                String latitude = String.valueOf(restaurantDetails.getLatitude());
                String longitude = String.valueOf(restaurantDetails.getLongitude());
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");


                try {
                    if (mapIntent.resolveActivity(getBaseActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                } catch (NullPointerException e) {
                    Log.e(TAG, "onClick: NullPointerException: Couldn't open map." + e.getMessage());
                    Toast.makeText(getActivity(), "Couldn't open map", Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkBoxStar.setChecked(restaurantDetails.getSubscribed());
        checkBoxStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO vi3: poslati zahtev o promeni stanja
                mPresenter.subscribeToRestaurant(restaurantDetails.getId());
            }
        });

        // TODO vi3: prikazati kuhinju
        if (restaurantDetails.getKitchens() != null) {
            mKitchensAdapter.addItems(restaurantDetails.getKitchens());
        } else {
            // TODO vi3: ne bi bilo lose prikazati prazan prikaz kada kuhinja nema
            Toast.makeText(getContext(),
                    "Nema kuhinja za ovaj restoran",
                    Toast.LENGTH_SHORT).show();
        }

        if (restaurantDetails.getImageUrl() != null) {
            Glide.with(this)
                    .load(restaurantDetails.getImageUrl())
                    .into(imageView);
        }
    }

    @Override
    public void successSubscription() {
        // Kada se korisnik uspesno pretplatio ili otplation, onda ne menjamo status zvezdice.
        // Ona se menja cim cekiramo.
    }

    @Override
    public void failSubscription() {
        // Zvezdica je cekirana, ali moramo je ponistiti.
        checkBoxStar.setChecked(!checkBoxStar.isChecked());
    }
}
