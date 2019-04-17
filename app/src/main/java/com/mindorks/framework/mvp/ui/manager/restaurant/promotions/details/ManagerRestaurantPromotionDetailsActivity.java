package com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsMvpPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerRestaurantPromotionDetailsActivity extends BaseActivity implements ManagerRestaurantPromotionDetailsMvpView {


    @Inject
    ManagerRestaurantPromotionDetailsMvpPresenter<ManagerRestaurantPromotionDetailsMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ManagerRestaurantPromotionDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_restaurant_promotion_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ManagerRestaurantPromotionDetailsActivity.this);

        setUp();
    }

    @Override
    protected void setUp() {

        setSupportActionBar(mToolbar);
        int promotionId = getIntent().getIntExtra("promotionId",-1);
        Toast.makeText(this,"A "+promotionId,Toast.LENGTH_SHORT).show();

    }
}
