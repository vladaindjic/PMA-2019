package com.mindorks.framework.mvp.ui.user.meal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.dish.UserDishActivity;
import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsFragment;
import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsNutritiveValuesAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishListAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserMealActivity extends BaseActivity implements UserMealMvpView, DishListAdapter.DishListItemCallback {

    @Inject
    UserMealMvpPresenter<UserMealMvpView> mPresenter;

    @BindView(R.id.txt_user_meal_name)
    TextView txtName;

    @BindView(R.id.user_meal_start_time_txt)
    TextView txtStart;

    @BindView(R.id.user_meal_end_time_txt)
    TextView txtEnd;

    @BindView(R.id.user_meal_details_txt_price_values)
    TextView txtPrice;


    @BindView(R.id.user_meal_details_txt_description_values)
    TextView txtDescription;

    @BindView(R.id.meal_details_btn_eat_me)
    Button btnEat;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    // potrebno za prikaz jela
    @Inject
    DishListAdapter mDishListAdapter;

    @Inject
    LinearLayoutManager mLayoutManagerDishes;

    @BindView(R.id.user_meal_details_dishes_recyclerview)
    RecyclerView mRecyclerViewDishes;


    // potrebno za prikaz jela
    @Inject
    UserDishDetailsNutritiveValuesAdapter mNutritiveValuesAdapter;

    @Inject
    LinearLayoutManager mLayoutManagerNutritiveValues;

    @BindView(R.id.user_meal_details_nutritive_values_recyclerview)
    RecyclerView mRecyclerViewNutritiveValues;

    MealResponse.MealDetails mealDetails = null;
    public static int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 54321;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserMealActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_meal);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(UserMealActivity.this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);


        Intent intent = getIntent();
        Long mealId = intent.getLongExtra("mealId", 0L);
        if (mealId == 0L) {
            Toast.makeText(this, "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }

        // potrebno za prikaz jela
        mLayoutManagerDishes.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewDishes.setLayoutManager(mLayoutManagerDishes);
        mRecyclerViewDishes.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewDishes.setAdapter(mDishListAdapter);

        // potrebno za prikaz nutritivnih vrednosti
        mLayoutManagerNutritiveValues.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewNutritiveValues.setLayoutManager(mLayoutManagerNutritiveValues);
        mRecyclerViewNutritiveValues.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewNutritiveValues.setAdapter(mNutritiveValuesAdapter);

        mDishListAdapter.setmCallback(this);
        mDishListAdapter.setBasePresenterForImageUrlProviding((BasePresenter)mPresenter);

        mPresenter.onViewPrepared(mealId);


    }

    @Override
    public void updateMealDetails(final MealResponse.MealDetails mealDetails) {
        this.mealDetails = mealDetails;

        if (mealDetails.getName() != null) {
            txtName.setText(mealDetails.getName());
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(getString(R.string.time_format));

        if (mealDetails.getStartTime() != null) {
            txtStart.setText(timeFormat.format(mealDetails.getStartTime()));
        }

        if (mealDetails.getEndTime() != null) {
            txtEnd.setText(timeFormat.format(mealDetails.getEndTime()));
        }

        if (mealDetails.getPrice() != null) {
            txtPrice.setText(String.format(Locale.US, "%.2f", mealDetails.getPrice()));
        }

        if (mealDetails.getDescription() != null) {
            txtDescription.setText(mealDetails.getDescription());
        }

        btnEat.setOnClickListener(new View.OnClickListener() {
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

                if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(UserMealActivity.this),
                        fitnessOptions)) {
                    GoogleSignIn.requestPermissions(
                            UserMealActivity.this, // your activity
                            GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                            GoogleSignIn.getLastSignedInAccount(UserMealActivity.this),
                            fitnessOptions);
                } else {
                    addNutritiveValues();
                }
            }
        });

        if (mealDetails.getDishList() != null) {
            // TODO vi3: ako nema jela, smisli empty view
            mDishListAdapter.addItems(mealDetails.getDishList());
        }

        if (mealDetails.getNutritiveValues() != null) {
            // TODO vi3: ako nema jela, smisli empty view
            mNutritiveValuesAdapter.addItems(mealDetails.getNutritiveValues());
        }

    }

    @Override
    public void openDishActivity(MenuResponse.Dish dish) {
        Intent intent = UserDishActivity.getStartIntent(this);
        intent.putExtra("dishId", dish.getId());
        startActivity(intent);
        //finish();
    }

    @Override
    public void onDishesEmptyViewRetryClick() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("Maksimalno me izignorise :(");
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                addNutritiveValues();
            }
        }
    }

    public void addNutritiveValues() {
        if (this.mealDetails == null) {
            Toast.makeText(this,
                    "Jelo nije prikazano kako treba",
                    Toast.LENGTH_SHORT).show();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long currentTime = cal.getTimeInMillis();
        cal.add(Calendar.MINUTE, -1);
//
        DataSource nutritionSource = new DataSource.Builder()
                .setAppPackageName(this)
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

        dishGoogleFit.getValue(Field.FIELD_FOOD_ITEM).setString(this.mealDetails.getName());
        dishGoogleFit.getValue(Field.FIELD_MEAL_TYPE).setInt(Field.MEAL_TYPE_LUNCH);
        for (DishDetailsResponse.NutritiveValue nv : this.mealDetails.getNutritiveValues()) {
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

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
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
                        Toast.makeText(UserMealActivity.this,
                                "Uspesno dodate nutritivne vrednosti za obrok: " + dishGoogleFit.getValue(Field.FIELD_FOOD_ITEM).asString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
