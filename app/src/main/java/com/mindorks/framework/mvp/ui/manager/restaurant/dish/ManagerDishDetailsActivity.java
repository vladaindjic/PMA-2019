package com.mindorks.framework.mvp.ui.manager.restaurant.dish;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerDishDetailsActivity extends BaseFragment implements
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


    @BindView(R.id.btn_eat_me)
    Button btnEatMe;


    public static ManagerDishDetailsActivity newInstance() {
        Bundle args = new Bundle();
        ManagerDishDetailsActivity fragment = new ManagerDishDetailsActivity();
        fragment.setArguments(args);
        return fragment;
    }


    public ManagerDishDetailsActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_dish_details, container, false);

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
        Long dishId = bundle.getLong("dishId");

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNutritiveValuesAdapter);

        mPresenter.onViewPrepared(dishId);
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
            Toast.makeText(getContext(),
                    "Nema nutritivnih vrednosti za ovo jelo",
                    Toast.LENGTH_SHORT).show();
        }

        btnEatMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "Pojeo si: " + dishDetails.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

}
