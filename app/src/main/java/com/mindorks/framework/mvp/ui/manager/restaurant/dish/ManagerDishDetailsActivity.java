package com.mindorks.framework.mvp.ui.manager.restaurant.dish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.DishRequestDto;
import com.mindorks.framework.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerDishDetailsActivity extends BaseActivity implements
        ManagerDishDetailsMvpView,ManagerDishNutitiveValueCallback {

    private static final String TAG = "ManagerDishDetailsActivity";

    @Inject
    ManagerDishDetailsMvpPresenter<ManagerDishDetailsMvpView> mPresenter;


    @BindView(R.id.manager_dish_details_img)
    ImageView imageView;

    @BindView(R.id.manager_dish_details_name)
    TextView txtViewName;

//    @BindView(R.id.manager_dish_details_kitchen)
//    TextView txtViewKitchen;

    @BindView(R.id.manager_dish_details_txt_price_values)
    TextView txtViewPrice;

    @BindView(R.id.manager_dish_details_txt_description_values)
    TextView txtViewDescription;

    @BindView(R.id.manager_nutritive_values_txt)
    EditText txtNutritionValue;

    // Kitchen
    @Inject
    ManagerDishDetailsNutritiveValuesAdapter mNutritiveValuesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.manager_dish_details_nutritive_values_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.nutritive_values_spinner)
    Spinner spinner;

    @BindView(R.id.dish_kitchen_spinner)
    Spinner kitchenSpinner;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> kitchenAdapter;

    List<String> nutritionValue;

    DishDetailsResponse.DishDetails dishDetailsOrginal;
    DishDetailsResponse.DishDetails dishDetailsEdited;
    private long dishId;


    public ManagerDishDetailsActivity() {
        // Required empty public constructor
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ManagerDishDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dish_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ManagerDishDetailsActivity.this);

        setUp();

    }

    @Override
    protected void setUp() {
        Bundle bundle = getIntent().getExtras();
        dishId = bundle.getLong("dishId",-1L);
        mNutritiveValuesAdapter.setmCallback(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNutritiveValuesAdapter);

        this.nutritionValue = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.nutrition_values)));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nutritionValue);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        mPresenter.getRestaurantKithen();

//        kitchenSpinner.setAdapter(adapter);

        //Ako je id razlicit od -1 ucitaj podatke pa prikazi.
        if (dishId != -1L) {
            mPresenter.onViewPrepared(dishId);
        } else {
            this.dishDetailsEdited = new DishDetailsResponse.DishDetails();
            this.dishDetailsEdited.setKitchen(new DishDetailsResponse.Kitchen());
            this.dishDetailsEdited.setNutritiveValues(new ArrayList<DishDetailsResponse.NutritiveValue>());
            this.dishDetailsOrginal = new DishDetailsResponse.DishDetails();
            this.dishDetailsOrginal.setKitchen(new DishDetailsResponse.Kitchen());
            this.dishDetailsOrginal.setNutritiveValues(new ArrayList<DishDetailsResponse.NutritiveValue>());
        }

    }

    @Override
    public void updateDishDetails(final DishDetailsResponse.DishDetails dishDetails) {
        this.dishDetailsEdited = dishDetails;
        this.saveOrginalDishState();

        if (dishDetails.getImageUrl() != null) {
            Glide.with(this)
                    .load(dishDetails.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
            // FIXME vi3: ako liniju ispod uklonim, nece da update-uje sliku
            imageView.setImageResource(R.drawable.login_bg);
        }

        if (dishDetails.getName() != null) {
            txtViewName.setText(dishDetails.getName());
        }
        this.setKitchenSpiner(dishDetails.getKitchen());
//       if (dishDetails.getKitchen() != null && dishDetails.getKitchen().getName() != null) {
//            txtViewKitchen.setText(dishDetails.getKitchen().getName());
//        }
        if (dishDetails.getPrice() != null) {
            txtViewPrice.setText(String.format(Locale.US, "%.2f", dishDetails.getPrice()));
        }
        if (dishDetails.getDescription() != null) {
            txtViewDescription.setText(dishDetails.getDescription());
        }

        if (dishDetails.getNutritiveValues() != null) {
            mNutritiveValuesAdapter.addItems(dishDetails.getNutritiveValues());
            this.nutritionValue = this.removeUsedNutirionValues(dishDetails.getNutritiveValues());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nutritionValue);
            spinner.setAdapter(adapter);
        } else {
            // TODO vi3: ne bi bilo lose prikazati prazan prikaz kada nema nutritivnih vrednosti
            Toast.makeText(this,
                    "Nema nutritivnih vrednosti za ovo jelo",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void setKitchenSpiner(DishDetailsResponse.Kitchen kitchen) {
        if(kitchen!=null){
            for(int i=0;i<kitchenAdapter.getCount();i++){
                if(kitchen.getName().toLowerCase().equals(kitchenAdapter.getItem(i).toLowerCase())){
                    kitchenSpinner.setSelection(i);
                }
            }
        }
    }

    @Override
    public void setKitchenAdapter(List<String> kitchens) {
        kitchenAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kitchens);
        kitchenSpinner.setAdapter(kitchenAdapter);
    }

    @Override
    public void back() {
        finish();
    }

    private void saveOrginalDishState() {
        this.dishDetailsOrginal = new DishDetailsResponse.DishDetails();
        this.dishDetailsOrginal.setId(this.dishDetailsEdited.getId());
        this.dishDetailsOrginal.setDescription(this.dishDetailsEdited.getDescription());
        DishDetailsResponse.Kitchen kitchen = new DishDetailsResponse.Kitchen();
        kitchen.setId(this.dishDetailsEdited.getKitchen().getId());
        kitchen.setName(this.dishDetailsEdited.getName());
        this.dishDetailsOrginal.setKitchen(kitchen);
        this.dishDetailsOrginal.setImageUrl(this.dishDetailsEdited.getImageUrl());
        this.dishDetailsOrginal.setName(this.dishDetailsEdited.getName());
        this.dishDetailsOrginal.setPrice(this.dishDetailsEdited.getPrice());
        List<DishDetailsResponse.NutritiveValue> nutritiveValues = new ArrayList<>();
        nutritiveValues.addAll(this.dishDetailsEdited.getNutritiveValues());
        this.dishDetailsOrginal.setNutritiveValues(nutritiveValues);
    }

    @OnClick(R.id.manager_dish_details_cancel_btn)
    public void cancelUpdate() {
        this.updateDishDetails(this.dishDetailsOrginal);

    }

    @OnClick(R.id.manager_dish_details_submit_btn)
    public void saveChanges(){
        System.out.println("Ovdije sam");
        this.dishDetailsEdited.setName(txtViewName.getText().toString());
        this.dishDetailsEdited.setPrice(Double.parseDouble(txtViewPrice.getText().toString()));
        this.dishDetailsEdited.setDescription(txtViewDescription.getText().toString());
        DishDetailsResponse.Kitchen kitchen = new DishDetailsResponse.Kitchen();
        kitchen.setName(kitchenSpinner.getSelectedItem().toString());
        this.dishDetailsEdited.setKitchen(kitchen);

        System.out.println(this.dishDetailsEdited.getNutritiveValues().size());


        DishRequestDto requestData = new DishRequestDto(this.dishDetailsEdited);
        if(dishId!=-1L) {
            mPresenter.updateDish(dishId,requestData);
        }else{
            System.out.println("PUT JELA");
            mPresenter.createDish(requestData);
        }
    }


    private List<String> removeUsedNutirionValues(List<DishDetailsResponse.NutritiveValue> nutritiveValues) {
        List<String> myValues = new ArrayList<>();
        List<String> dishValues = new ArrayList<>();

        for (String value : nutritionValue) {
            myValues.add(value.toLowerCase());
        }

        for (DishDetailsResponse.NutritiveValue value : nutritiveValues) {
            dishValues.add(value.getName().toLowerCase());
        }

        List<String> union = new ArrayList<>(myValues);
        union.addAll(dishValues);

        List<String> intersection = new ArrayList<>(myValues);
        intersection.retainAll(dishValues);


        union.removeAll(intersection);
        return union;
    }


    @OnClick(R.id.manager_add_nutritive_value_btn)
    public void addNutritionValue() {
        String nutritionValue = spinner.getSelectedItem().toString();
        System.out.println(spinner.getSelectedItemPosition());
        Toast.makeText(this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        System.out.println(this.txtNutritionValue.getText().toString());
        switch (nutritionValue.toLowerCase()) {
            case "kalorijska vrednost": {
                DishDetailsResponse.NutritiveValue value = new DishDetailsResponse.NutritiveValue();
                value.setName(this.spinner.getSelectedItem().toString());
                value.setValue(Double.parseDouble(this.txtNutritionValue.getText().toString()));
                value.setUnit("kcal");
                this.dishDetailsEdited.getNutritiveValues().add(value);
                this.mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
                this.nutritionValue.remove(this.spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
                break;
            }
            case "masti": {
                DishDetailsResponse.NutritiveValue value = new DishDetailsResponse.NutritiveValue();
                value.setName(this.spinner.getSelectedItem().toString());
                value.setValue(Double.parseDouble(this.txtNutritionValue.getText().toString()));
                value.setUnit("g");
                this.dishDetailsEdited.getNutritiveValues().add(value);
                this.mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
                this.nutritionValue.remove(this.spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
                break;
            }
            case "proteini": {
                DishDetailsResponse.NutritiveValue value = new DishDetailsResponse.NutritiveValue();
                value.setName(this.spinner.getSelectedItem().toString());
                value.setValue(Double.parseDouble(this.txtNutritionValue.getText().toString()));
                value.setUnit("g");
                this.dishDetailsEdited.getNutritiveValues().add(value);
                this.mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
                this.nutritionValue.remove(this.spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
                break;
            }
            case "ugljeni hidrati": {
                DishDetailsResponse.NutritiveValue value = new DishDetailsResponse.NutritiveValue();
                value.setName(this.spinner.getSelectedItem().toString());

                value.setValue(Double.parseDouble(this.txtNutritionValue.getText().toString()));
                value.setUnit("g");
                this.dishDetailsEdited.getNutritiveValues().add(value);
                this.mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
                this.nutritionValue.remove(this.spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
                break;
            }
        }
        this.txtNutritionValue.setText("");


    }

    @Override
    public void deleteNutritiveValue(String name) {
        List<DishDetailsResponse.NutritiveValue> nutritiveValues = this.dishDetailsEdited.getNutritiveValues();
        for(DishDetailsResponse.NutritiveValue value : nutritiveValues){
            if(value.getName().toLowerCase().equals(name.toLowerCase())){
                this.dishDetailsEdited.getNutritiveValues().remove(value);
                this.nutritionValue.add(value.getName());
                break;
            }
        }
        mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
        adapter.notifyDataSetChanged();
    }
}
