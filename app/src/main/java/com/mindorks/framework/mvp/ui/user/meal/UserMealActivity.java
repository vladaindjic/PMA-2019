package com.mindorks.framework.mvp.ui.user.meal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.dish.UserDishActivity;
import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsNutritiveValuesAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishListAdapter;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
                // TODO vi3: zapisati u Google healt
                Toast.makeText(UserMealActivity.this, "Pojeo si " + mealDetails.getName(),
                        Toast.LENGTH_SHORT).show();
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
}
