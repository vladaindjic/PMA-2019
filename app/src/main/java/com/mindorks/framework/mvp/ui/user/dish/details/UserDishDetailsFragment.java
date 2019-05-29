package com.mindorks.framework.mvp.ui.user.dish.details;


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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;

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
        UserDishDetailsMvpView {

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

        mPresenter.onViewPrepared(dishId);
    }

    @Override
    public void updateDishDetails(final DishDetailsResponse.DishDetails dishDetails) {

        Glide.with(this)
                .load(((BasePresenter)mPresenter).getImageUrlFor(BasePresenter.ENTITY_RESTAURANT,
                        dishDetails.getImageUrl()))
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

                DataSource nutritionSource = new DataSource.Builder()
                        .setType(DataSource.TYPE_RAW)
                        .setDataType(DataType.TYPE_NUTRITION)
                        .build();

                DataPoint banana = DataPoint.create(nutritionSource);
                banana.setTimestamp(new Date().getTime(), TimeUnit.MILLISECONDS);
                banana.getValue(Field.FIELD_FOOD_ITEM).setString("banana");
                banana.getValue(Field.FIELD_MEAL_TYPE).setInt(Field.MEAL_TYPE_SNACK);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_TOTAL_FAT, 0.4f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SODIUM, 1f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SATURATED_FAT, 0.1f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_PROTEIN, 1.3f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_TOTAL_CARBS, 27.0f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_CHOLESTEROL, 0.0f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_CALORIES, 105.0f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_SUGAR, 14.0f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_DIETARY_FIBER, 3.1f);
                banana.getValue(Field.FIELD_NUTRIENTS).setKeyValue(Field.NUTRIENT_POTASSIUM, 422f);


                Toast.makeText(getContext(),
                        "Pojeo si: " + dishDetails.getName(),
                        Toast.LENGTH_SHORT).show();


            }
        });

    }

}
