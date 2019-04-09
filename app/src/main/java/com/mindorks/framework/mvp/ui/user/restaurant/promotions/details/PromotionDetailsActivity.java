package com.mindorks.framework.mvp.ui.user.restaurant.promotions.details;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class PromotionDetailsActivity extends BaseActivity implements PromotionDetailsMvpView {

    @Inject
    PromotionDetailsMvpPresenter<PromotionDetailsMvpView> mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(PromotionDetailsActivity.this);

        setUp();
    }

    @Override
    protected void setUp() {



    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PromotionDetailsActivity.class);
        return intent;
    }
}
