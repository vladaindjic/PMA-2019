package com.mindorks.framework.mvp.ui.user.dish.details;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.dish.UserDishActivity;
import com.mindorks.framework.mvp.ui.user.dish.util.AddNutritiveValuesCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDishDetailsFragment extends BaseFragment implements
        UserDishDetailsMvpView, AddNutritiveValuesCallback {

    private static final String TAG = "UserDishDetailsFragment";

    @Inject
    UserDishDetailsMvpPresenter<UserDishDetailsMvpView> mPresenter;


    @BindView(R.id.user_dish_details_img)
    ImageView imageView;

    @BindView(R.id.user_dish_details_name)
    TextView txtViewName;

    @BindView(R.id.user_dish_details_kitchen)
    TextView txtViewKitchen;

    @BindView(R.id.user_dish_details_txt_price_values)
    TextView txtViewPrice;

    @BindView(R.id.user_dish_details_txt_description_values)
    TextView txtViewDescription;

    // Kitchen
    @Inject
    UserDishDetailsNutritiveValuesAdapter mNutritiveValuesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.user_dish_details_nutritive_values_recyclerview)
    RecyclerView mRecyclerView;


    @BindView(R.id.btn_eat_me)
    Button btnEatMe;

    DishDetailsResponse.DishDetails dishDetails = null;

    public static int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 12345;

    public static UserDishDetailsFragment newInstance() {
        Bundle args = new Bundle();
        UserDishDetailsFragment fragment = new UserDishDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public UserDishDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_dish_details, container, false);

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
        Long dishId = getBaseActivity().getIntent().getLongExtra("dishId", 0L);
        System.out.println("***************************** JEBES LI ME " + dishId);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNutritiveValuesAdapter);

        // vi3 GF: da nas aktivnost moze pozvati kada joj se permisija odobri
        ((UserDishActivity) getBaseActivity()).setAddNutritiveValuesCallback(this);

        mPresenter.onViewPrepared(dishId);
    }

    @Override
    public void updateDishDetails(final DishDetailsResponse.DishDetails dishDetails) {
        // vi3: da lakse mogu da pristupim jelu
        this.dishDetails = dishDetails;
        Glide.with(this)
                .load(((BasePresenter) mPresenter).getImageUrlFor(BasePresenter.ENTITY_RESTAURANT,
                        dishDetails.getImageUrl()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
        // FIXME vi3: ako liniju ispod uklonim, nece da update-uje sliku
        //imageView.setImageResource(R.drawable.login_bg);

        if (dishDetails.getName() != null) {
            txtViewName.setText(dishDetails.getName());
        }
        if (dishDetails.getKitchen() != null && dishDetails.getKitchen().getName() != null) {
            txtViewKitchen.setText(dishDetails.getKitchen().getName());
        }
        if (dishDetails.getPrice() != null) {
            txtViewPrice.setText(String.format(Locale.US, "%.2f", dishDetails.getPrice()));
        }
        if (dishDetails.getDescription() != null) {
            txtViewDescription.setText(dishDetails.getDescription());
        }

        if (dishDetails.getNutritiveValues() != null) {
            mNutritiveValuesAdapter.addItems(dishDetails.getNutritiveValues());
        } else {
            // TODO vi3: ne bi bilo lose prikazati prazan prikaz kada nema nutritivnih vrednosti
            Toast.makeText(getContext(),
                    "Nema nutritivnih vrednosti za ovo jelo",
                    Toast.LENGTH_SHORT).show();
        }

        btnEatMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FitnessOptions fitnessOptions = FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_WRITE)
                        .addDataType(DataType.TYPE_NUTRITION, FitnessOptions.ACCESS_WRITE)
                        .addDataType(DataType.AGGREGATE_NUTRITION_SUMMARY, FitnessOptions.ACCESS_WRITE)
                        .build();

                if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(getBaseActivity()),
                        fitnessOptions)) {
                    GoogleSignIn.requestPermissions(
                            getBaseActivity(), // your activity
                            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                            GoogleSignIn.getLastSignedInAccount(getBaseActivity()),
                            fitnessOptions);
                } else {
                    addNutritiveValues();
                }


            }
        });

    }


    @Override
    public void addNutritiveValues() {
        if (this.dishDetails == null) {
            Toast.makeText(getContext(),
                    "Jelo nije prikazano kako treba",
                    Toast.LENGTH_SHORT).show();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long currentTime = cal.getTimeInMillis();
        cal.add(Calendar.MINUTE, -1);
//
        DataSource nutritionSource = new DataSource.Builder()
                .setAppPackageName(getBaseActivity())
                .setType(DataSource.TYPE_RAW)
                .setDataType(DataType.TYPE_NUTRITION)
                .build();

        DataSet dataSet = DataSet.create(nutritionSource);
        // For each data point, specify a start time, end time, and the data value -- in this case,
        // the number of new steps.
        final DataPoint dishGoogleFit =
                dataSet.createDataPoint().setTimestamp(currentTime, TimeUnit.MILLISECONDS);
        //.setTimeInterval(startTime, endTime,
        //TimeUnit.MILLISECONDS);

        dishGoogleFit.getValue(Field.FIELD_FOOD_ITEM).setString(this.dishDetails.getName());
        dishGoogleFit.getValue(Field.FIELD_MEAL_TYPE).setInt(Field.MEAL_TYPE_LUNCH);
        for (DishDetailsResponse.NutritiveValue nv : this.dishDetails.getNutritiveValues()) {
            if (nv.getName().toUpperCase().startsWith("KAL")) {
                dishGoogleFit.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_CALORIES
                        , nv.getValue().floatValue());
                System.out.println("VI3: KALORIJE");
            } else if (nv.getName().toUpperCase().startsWith("MAS")) {
                dishGoogleFit.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_TOTAL_FAT, nv.getValue().floatValue());
                System.out.println("VI3: MASTI");
            } else if (nv.getName().toUpperCase().startsWith("PROT")) {
                dishGoogleFit.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_PROTEIN, nv.getValue().floatValue());
                System.out.println("VI3: PROTEINI");
            } else if (nv.getName().toUpperCase().startsWith("UGLJ")) {
                dishGoogleFit.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_TOTAL_CARBS, nv.getValue().floatValue());
                System.out.println("VI3: UGLJENI HIDRATI");
            } else {

            }
        }

//        dish.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SODIUM, 1f);
//        dish.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SATURATED_FAT, 0.1f);
//        dish.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_CHOLESTEROL, 0.0f);
//        dish.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SUGAR, 14.0f);
//        dish.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_DIETARY_FIBER, 3.1f);
//        dish.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_POTASSIUM, 422f);

        dataSet.add(dishGoogleFit);

        Fitness.getHistoryClient(getBaseActivity(), GoogleSignIn.getLastSignedInAccount(getBaseActivity()))
                .insertData(dataSet)
//                .readData(readRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("*************Complete " + task.isSuccessful());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(),
                                "Uspesno dodate nutritivne vrednosti za jelo: " + dishGoogleFit.getValue(Field.FIELD_FOOD_ITEM).asString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }


}
