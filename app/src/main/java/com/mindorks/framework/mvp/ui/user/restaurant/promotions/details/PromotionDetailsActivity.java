package com.mindorks.framework.mvp.ui.user.restaurant.promotions.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromotionDetailsActivity extends BaseActivity implements PromotionDetailsMvpView {

    @Inject
    PromotionDetailsMvpPresenter<PromotionDetailsMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.promotion_details_image_view)
    ImageView promotionImageView;

    @BindView(R.id.promotion_details_title_text_view)
    TextView promotionTitleTextView;

    @BindView(R.id.promotion_details_subtitle_text_view)
    TextView promotionSubTitleTextView;

    @BindView(R.id.promotion_details_text_view_value)
    TextView promotionDetailsTextView;

    @BindView(R.id.promotion_price_text_view_value)
    TextView promotionPriceTextView;

    @BindView(R.id.promotion_duration_text_view_value)
    TextView promotionDurationTextView;

    private PromotionDetailsResponse.Promotion promotion;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PromotionDetailsActivity.class);
        return intent;
    }

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
        setSupportActionBar(mToolbar);
        mPresenter.onViewPrepared();
    }

    @Override
    public void updatePromotion(PromotionDetailsResponse.Promotion promotion) {

        this.promotion = promotion;

        if (promotion.getImageUrl() != null) {
            Glide.with(promotionImageView.getContext())
                    .load(promotion.getImageUrl())
                    .into(promotionImageView);
        }

        if (promotion.getTitle() != null) {
            promotionTitleTextView.setText(promotion.getTitle());
        }

        if (promotion.getSubTitle() != null) {
            promotionSubTitleTextView.setText(promotion.getSubTitle());
        }

        if (promotion.getDetails() != null) {
            promotionDetailsTextView.setText(promotion.getDetails());
        }

        if (promotion.getPrice() != null) {
            promotionPriceTextView.setText(promotion.getPrice());
        }

        if (promotion.getDuration() != null) {
            promotionDurationTextView.setText(promotion.getDuration());
        }

    }
}
