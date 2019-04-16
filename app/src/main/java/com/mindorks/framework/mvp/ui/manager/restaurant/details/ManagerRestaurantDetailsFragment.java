package com.mindorks.framework.mvp.ui.manager.restaurant.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
public class ManagerRestaurantDetailsFragment extends BaseFragment implements
        ManagerRestaurantDetailsMvpView {

    private static final String TAG = "ManagerRestaurantDetailsFragment";


    @Inject
    ManagerRestaurantDetailsMvpPresenter<ManagerRestaurantDetailsMvpView> mPresenter;


    @BindView(R.id.manager_restaurant_details_img)
    ImageView imageView;

    @BindView(R.id.manager_restaurant_details_name)
    EditText editName;

    @BindView(R.id.manager_restaurant_details_address)
    EditText editAddress;

    @BindView(R.id.manager_dish_details_txt_description_values)
    CheckBox checkBoxDelivery;

    @BindView(R.id.manager_dish_details_edit_txt_price_values)
    EditText editPhone;

    @BindView(R.id.manager_restaurant_details_edit_txt_work_time_values)
    EditText editWorkTime;

    @BindView(R.id.manager_restaurant_details_edit_txt_email_values)
    EditText editEmail;

    @BindView(R.id.manager_restaurant_details_submit_btn)
    Button btnSubmit;
    @BindView(R.id.manager_restaurant_details_cancel_btn)
    Button btnCancel;

    // Kitchen
    @Inject
    ManagerRestaurantDetailsKitchensAdapter mKitchensAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.manager_dish_details_nutritive_values_recyclerview)
    RecyclerView mRecyclerView;

    private RestaurantDetailsResponse.RestaurantDetails restaurantDetails;

    public static ManagerRestaurantDetailsFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantDetailsFragment fragment = new ManagerRestaurantDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerRestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_restaurant_details, container, false);

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
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mKitchensAdapter);

        // TODO vi3: ovde treba ispraviti API poziv da vrati restoran managera koji je ulogovan
        mPresenter.onViewPrepared(1L);

        // mislim da ima vise logike ovo ostavit ovde, nego svaki put setovati
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelUpdatingRestaurantDetails();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUpdatingRestaurantDetails();
            }
        });
    }

    @Override
    public void updateRestaurantDetails(final RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;

        // azuriranje polja koja se prikazuje
        editName.setText(restaurantDetails.getName());
        editAddress.setText(restaurantDetails.getAddress());

        checkBoxDelivery.setChecked(restaurantDetails.isDelivery());
        editPhone.setText(restaurantDetails.getPhone());

        editWorkTime.setText(restaurantDetails.getWorkTime());
        editEmail.setText(restaurantDetails.getEmail());

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


    private void cancelUpdatingRestaurantDetails() {
        // posto smo memoizovali restoran koji smo svukli sa neta
        // samo ih ponovo postavimo i to je to
        // sve izmene ce biti ponostene
        this.updateRestaurantDetails(this.restaurantDetails);
    }

    private void submitUpdatingRestaurantDetails() {
        // TODO vi3: ovde treba ici API poziv da se uradi update

        RestaurantDetailsResponse.RestaurantDetails restaurantDetails = new RestaurantDetailsResponse.RestaurantDetails();
        restaurantDetails.setId(this.restaurantDetails.getId());
        restaurantDetails.setName(this.editName.getText().toString());
        restaurantDetails.setAddress(this.editAddress.getText().toString());
        restaurantDetails.setEmail(this.editEmail.getText().toString());
        restaurantDetails.setDelivery(this.checkBoxDelivery.isChecked());
        restaurantDetails.setPhone(this.editPhone.getText().toString());
        restaurantDetails.setWorkTime(this.editWorkTime.getText().toString());

        mPresenter.submitRestaurantDetails(restaurantDetails);

        // TODO vi3: takodje treba videti koji je najbolji nacin da se uradi
        // update fotografije
    }
}
