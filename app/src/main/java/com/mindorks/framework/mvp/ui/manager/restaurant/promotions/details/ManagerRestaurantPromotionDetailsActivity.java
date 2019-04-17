package com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsMvpPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerRestaurantPromotionDetailsActivity extends BaseActivity implements ManagerRestaurantPromotionDetailsMvpView {


    @Inject
    ManagerRestaurantPromotionDetailsMvpPresenter<ManagerRestaurantPromotionDetailsMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private PromotionDetailsResponse.Promotion orginalPromotion;
    private PromotionDetailsResponse.Promotion editedPromotion;


    //View polja

    @BindView(R.id.promotion_details_title_text_edit)
    EditText titleEditText;

    @BindView(R.id.promotion_details_subtitle_text_edit)
    EditText subtitleEditText;

    @BindView(R.id.promotion_details_text_edit)
    EditText detaildEditText;

    @BindView(R.id.promotion_price_text_edit)
    EditText priceEditText;

    @BindView(R.id.promotion_duration_text_edit)
    EditText durationEditText;

    @BindView(R.id.promotion_details_image_view)
    ImageView imageView;


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
        //Ako je id razlicit od -1 ucitaj podatke pa prikazi.
        if(promotionId != -1) {
            mPresenter.loadData(promotionId);
        }else {
            this.editedPromotion = new PromotionDetailsResponse.Promotion();
        }

    }

    @Override
    public void updatePromotion(PromotionDetailsResponse.Promotion promotion) {

        this.editedPromotion = promotion;

        this.saveOriginalPromotion();


        if (promotion.getImageUrl() != null) {
            Glide.with(imageView.getContext())
                    .load(promotion.getImageUrl())
                    .into(imageView);
        }

        if (promotion.getTitle() != null) {
            titleEditText.setText(promotion.getTitle());
        }

        if (promotion.getSubTitle() != null) {
            subtitleEditText.setText(promotion.getSubTitle());
        }

        if (promotion.getDetails() != null) {
            detaildEditText.setText(promotion.getDetails());
        }

        if (promotion.getPrice() != null) {
            priceEditText.setText(promotion.getPrice());
        }

        if (promotion.getDuration() != null) {
            durationEditText.setText(promotion.getDuration());
        }
    }

    @OnClick(R.id.submit_promotion_edit)
    public void submitPromotionEdit(){

        this.editedPromotion.setDetails(this.detaildEditText.getText().toString());
        this.editedPromotion.setTitle(this.titleEditText.getText().toString());
        this.editedPromotion.setSubTitle(this.subtitleEditText.getText().toString());
        this.editedPromotion.setPrice(this.priceEditText.getText().toString());
        this.editedPromotion.setDuration(this.durationEditText.getText().toString());

        mPresenter.savePromotion(this.editedPromotion);
    }


    @OnClick(R.id.cancel_promotion_edit)
    public void cancelPromotionEdit(){
        //Vrati vrednosti na stare
        this.updatePromotion(this.orginalPromotion);

    }


    private void saveOriginalPromotion(){
        this.orginalPromotion = new PromotionDetailsResponse.Promotion();

        this.orginalPromotion.setId(this.editedPromotion.getId());
        this.orginalPromotion.setTitle(this.editedPromotion.getTitle());
        this.orginalPromotion.setSubTitle(this.editedPromotion.getSubTitle());
        this.orginalPromotion.setImageUrl(this.editedPromotion.getImageUrl());
        this.orginalPromotion.setDetails(this.editedPromotion.getDetails());
        this.orginalPromotion.setPrice(this.editedPromotion.getPrice());
        this.orginalPromotion.setDuration(this.editedPromotion.getDuration());

    }
}
