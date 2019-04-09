package com.mindorks.framework.mvp.ui.user.restaurant.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final String TAG = "UserRestaurantDetailsFragment";

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

    @BindView(R.id.user_restaurant_details_checkbox_delivery_values)
    CheckBox checkBoxDelivery;

    @BindView(R.id.user_restaurant_details_txt_phone_values)
    TextView txtViewPhone;

    @BindView(R.id.user_restaurant_details_txt_work_time_values)
    TextView txtViewWorkTime;

    @BindView(R.id.user_restaurant_details_txt_email_values)
    TextView txtViewEmail;

    @BindView(R.id.btn_how_to_find_us)
    Button btnHowToFindUs;

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
            // mRestaurantsListAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        Long restaurantId = bundle.getLong("restaurantId");
        mPresenter.onViewPrepared(restaurantId);
    }

    @Override
    public void updateRestaurantDetails(final RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;

        // azuriranje polja koja se prikazuje
        txtViewName.setText(restaurantDetails.getName());
        txtViewAddress.setText(restaurantDetails.getAddress());

        checkBoxDelivery.setChecked(restaurantDetails.isDelivery());
        txtViewPhone.setText(restaurantDetails.getPhone());
        txtViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO vi3: intent koji ce otvoriti pozivanje broja
                Toast.makeText(getContext(),
                        "Treba pozvati broj: " + restaurantDetails.getPhone(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        txtViewWorkTime.setText(restaurantDetails.getWorkTime());
        txtViewEmail.setText(restaurantDetails.getEmail());

        btnHowToFindUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO vi3: integracija sa Google mapama
                Toast.makeText(getContext(),
                        "Samo jako do lokacije: " + restaurantDetails.getAddress(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // TODO vi3: postaviti stanje zvezde na osnovu dobijenih podataka
        // verovatno ce nam trebati poseban poziv koji ce porveriti da li je restoran
        // u favourites-ima
        checkBoxStar.setChecked(true);
        // TODO vi3: prikazati kuhinju
    }
}
