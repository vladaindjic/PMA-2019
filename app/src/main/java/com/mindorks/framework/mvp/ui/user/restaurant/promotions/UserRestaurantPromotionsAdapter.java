package com.mindorks.framework.mvp.ui.user.restaurant.promotions;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRestaurantPromotionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private UserRestaurantPromotionsCallback mCallback;

    private List<RestaurantPromotionsResponse.Promotion> mRestaurantPromotionsList;

    public UserRestaurantPromotionsAdapter(List<RestaurantPromotionsResponse.Promotion> mRestaurantPromotionsList) {
        this.mRestaurantPromotionsList = mRestaurantPromotionsList;
    }

    public void setmCallback(UserRestaurantPromotionsCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void addItems(List<RestaurantPromotionsResponse.Promotion> promotionList) {
        mRestaurantPromotionsList.addAll(promotionList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new UserRestaurantPromotionsAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.user_restaurant_promotions_list_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new UserRestaurantPromotionsAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mRestaurantPromotionsList != null && mRestaurantPromotionsList.size() > 0) {
            return mRestaurantPromotionsList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRestaurantPromotionsList != null && mRestaurantPromotionsList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }


    /**
     * ViewHolder
     * Klasa koja definise kako izgleda item pojedinacne promocije
     */
    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.promotions_list_item_image_view)
        ImageView promotionImageView;

        @BindView(R.id.promotions_list_item_text_view)
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        protected void clear() {
            promotionImageView.setImageDrawable(null);
            titleTextView.setText("");
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            final RestaurantPromotionsResponse.Promotion promotion = mRestaurantPromotionsList.get(position);

            if (promotion.getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(promotion.getImageUrl())
//                        .asBitmap()
//                        .centerCrop()
                        .into(promotionImageView);
            }

            if (promotion.getTitle() != null) {
                titleTextView.setText(promotion.getTitle());
            }
        }
    }


    /**
     * EmptyViewHolder
     */
    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.btn_retry)
        Button retryButton;

        @BindView(R.id.tv_message)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @OnClick(R.id.btn_retry)
        void onRetryClick() {
            if (mCallback != null)
                mCallback.onRestaurantsEmptyViewRetryClick();
        }
    }


}
