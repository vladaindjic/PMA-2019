package com.mindorks.framework.mvp.ui.manager.restaurant.dish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerDishDetailsActivity extends BaseActivity implements
        ManagerDishDetailsMvpView {

    private static final String TAG = "ManagerDishDetailsActivity";

    @Inject
    ManagerDishDetailsMvpPresenter<ManagerDishDetailsMvpView> mPresenter;


    @BindView(R.id.manager_dish_details_img)
    ImageView imageView;

    @BindView(R.id.manager_dish_details_name)
    TextView txtViewName;

    @BindView(R.id.manager_dish_details_kitchen)
    TextView txtViewKitchen;

    @BindView(R.id.manager_dish_details_txt_price_values)
    TextView txtViewPrice;

    @BindView(R.id.manager_dish_details_txt_description_values)
    TextView txtViewDescription;

    // Kitchen
    @Inject
    ManagerDishDetailsNutritiveValuesAdapter mNutritiveValuesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.manager_dish_details_nutritive_values_recyclerview)
    RecyclerView mRecyclerView;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ManagerDishDetailsActivity.class);
        return intent;
    }


    public ManagerDishDetailsActivity() {
        // Required empty public constructor
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
        Long dishId = bundle.getLong("dishId");

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNutritiveValuesAdapter);

        //Ako je id razlicit od -1 ucitaj podatke pa prikazi.
        if(dishId != -1) {
            mPresenter.onViewPrepared(dishId);
        }else {
            this.updateDishDetails(new DishDetailsResponse.DishDetails());
        }

    }

    @Override
    public void updateDishDetails(final DishDetailsResponse.DishDetails dishDetails) {

        if (dishDetails.getImageUrl() != null) {
            Glide.with(this)
                    .load(dishDetails.getImageUrl())
                    .into(imageView);
            // FIXME vi3: ako liniju ispod uklonim, nece da update-uje sliku
            imageView.setImageResource(R.drawable.login_bg);
        }

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
            Toast.makeText(this,
                    "Nema nutritivnih vrednosti za ovo jelo",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
