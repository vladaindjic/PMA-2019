package com.mindorks.framework.mvp.ui.manager.restaurant.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantDetailsFragment extends BaseFragment implements
        ManagerRestaurantDetailsMvpView, ManagerRestaurantDetailsKitchensAdapter.ManagerRestaurantDetailsKitchensAdapterCallback {

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

    @BindView(R.id.manager_kitchens_autocomplete_txt)
    AutoCompleteTextView autoKitchen;

    @BindView(R.id.manager_add_kitchen_btn)
    Button btnAddKitchen;

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

    ManagerKitchenArrayAdapter mKitchenArrayAdapter;

    private RestaurantDetailsResponse.Kitchen typedKitchen;

    private RestaurantDetailsResponse.RestaurantDetails restaurantDetails;

    private RestaurantDetailsResponse.RestaurantDetails originalRestaurantDetails;

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
            mKitchensAdapter.setmCallback(this);
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
        mPresenter.getAndPrepareKitchensForAutocomplete();

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

        btnAddKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKitchenToRestaurant();
            }
        });

        // kada korisnik izabere kuhinju, mi nju sacuvamo
        autoKitchen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                if (o instanceof RestaurantDetailsResponse.Kitchen) {
                    memoizeTypedKitchen((RestaurantDetailsResponse.Kitchen) o);
                }
            }
        });

        // kada se nesto otkuca, mi ponistimo kuhinju iz izbora
        autoKitchen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                removeTypedKitchen();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeTypedKitchen();
            }

            @Override
            public void afterTextChanged(Editable s) {
                removeTypedKitchen();
            }
        });

        // podrazumevano uklanjamo kuhinju
        removeTypedKitchen();
    }

    @Override
    public void prepareKitchensForAutocomplete(List<RestaurantDetailsResponse.Kitchen> kitchenList) {
        mKitchenArrayAdapter = new ManagerKitchenArrayAdapter(getContext(),
                R.id.manager_autocomplete_kitchen_list_item_name, kitchenList);
        // cim prvo slovo unese, nesto ce se prikazati
        autoKitchen.setThreshold(1);
        autoKitchen.setAdapter(mKitchenArrayAdapter);
        if (this.restaurantDetails != null) {
            mKitchenArrayAdapter.checkKitchensThatAreInRestaurantAndUpdateList(this.restaurantDetails);
        }
    }

    @Override
    public void updateRestaurantDetails(final RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
        this.saveOriginalRestaurantDetailsState();

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
        // poizbacuj sve kuhinje
        mKitchenArrayAdapter.checkKitchensThatAreInRestaurantAndUpdateList(this.restaurantDetails);
    }

    // cuvamo originalno stanje
    private void saveOriginalRestaurantDetailsState() {
        this.originalRestaurantDetails = new RestaurantDetailsResponse.RestaurantDetails();

        this.originalRestaurantDetails.setId(this.restaurantDetails.getId());
        this.originalRestaurantDetails.setName(this.restaurantDetails.getName());
        this.originalRestaurantDetails.setEmail(this.restaurantDetails.getEmail());
        this.originalRestaurantDetails.setPhone(this.restaurantDetails.getPhone());
        this.originalRestaurantDetails.setAddress(this.restaurantDetails.getAddress());
        this.originalRestaurantDetails.setDelivery(this.restaurantDetails.isDelivery());
        this.originalRestaurantDetails.setImageUrl(this.restaurantDetails.getImageUrl());
        this.originalRestaurantDetails.setWorkTime(this.restaurantDetails.getWorkTime());

        this.originalRestaurantDetails.setKitchens(new ArrayList<RestaurantDetailsResponse.Kitchen>());
        this.originalRestaurantDetails.getKitchens().addAll(this.restaurantDetails.getKitchens());

    }

    private void cancelUpdatingRestaurantDetails() {
        // posto smo memoizovali restoran koji smo svukli sa neta
        // samo ih ponovo postavimo i to je to
        // sve izmene ce biti ponostene
        this.updateRestaurantDetails(this.originalRestaurantDetails);
    }

    private void submitUpdatingRestaurantDetails() {
        RestaurantDetailsResponse.RestaurantDetails restaurantDetails = new RestaurantDetailsResponse.RestaurantDetails();
        restaurantDetails.setId(this.restaurantDetails.getId());
        restaurantDetails.setName(this.editName.getText().toString());
        restaurantDetails.setAddress(this.editAddress.getText().toString());
        restaurantDetails.setEmail(this.editEmail.getText().toString());
        restaurantDetails.setDelivery(this.checkBoxDelivery.isChecked());
        restaurantDetails.setPhone(this.editPhone.getText().toString());
        restaurantDetails.setWorkTime(this.editWorkTime.getText().toString());
        restaurantDetails.setKitchens(this.restaurantDetails.getKitchens());

        mPresenter.submitRestaurantDetails(restaurantDetails);

        // TODO vi3: takodje treba videti koji je najbolji nacin da se uradi
        // update fotografije
    }

    // dodajemo kuhinju na restoran
    private void addKitchenToRestaurant() {
        // dodajemo izabranu kuhinju u restoran
        this.restaurantDetails.getKitchens().add(this.typedKitchen);
        // update-ujemo samo prikaz kuhinja
        mKitchensAdapter.addItems(this.restaurantDetails.getKitchens());
        // moramo odraditi i update svih kuhinja u adapteru
        mKitchenArrayAdapter.checkKitchensThatAreInRestaurantAndUpdateList(this.restaurantDetails);
        // ponistavamo unos
        this.autoKitchen.setText("");
    }

    // uklanjamo kuhinju sa restorana
    @Override
    public void removeKitchenFromRestaurantDetails(RestaurantDetailsResponse.Kitchen kitchen) {
        // uklanjamo kuhinju
        this.restaurantDetails.getKitchens().remove(kitchen);
        // update-ujemo samo prikaz kuhinja
        mKitchensAdapter.addItems(this.restaurantDetails.getKitchens());
        // moramo odraditi i update svih kuhinja u adapteru
        mKitchenArrayAdapter.checkKitchensThatAreInRestaurantAndUpdateList(this.restaurantDetails);
    }

    // memoizujemo kuhinju
    private void memoizeTypedKitchen(RestaurantDetailsResponse.Kitchen kitchen) {
        this.typedKitchen = kitchen;
        // kada zapamtimo kuhinju, dozvolimo da dugme za dodavanje moze da se klikne
        this.btnAddKitchen.setClickable(true);
    }

    // uklanjamo memoizovanu kuhinju
    private void removeTypedKitchen() {
        this.typedKitchen = null;
        // ponistimo kuhinju, a samim tim i mogucnost pritiska dugmeta za dodavanje
        this.btnAddKitchen.setClickable(false);
    }


    // FIXME vi3: eventualno blokiraj unos kuhinje, ako si dodao sve kuhinje na restoran
}
