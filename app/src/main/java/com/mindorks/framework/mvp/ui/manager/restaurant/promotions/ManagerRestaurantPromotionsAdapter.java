package com.mindorks.framework.mvp.ui.manager.restaurant.promotions;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerRestaurantPromotionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private ManagerRestaurantPromotionsCallback mCallback;

    private List<RestaurantPromotionsResponse.Promotion> mRestaurantPromotionsList;

    public ManagerRestaurantPromotionsAdapter(List<RestaurantPromotionsResponse.Promotion> mRestaurantPromotionsList) {
        this.mRestaurantPromotionsList = mRestaurantPromotionsList;
    }

    public void setmCallback(ManagerRestaurantPromotionsCallback mCallback) {
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
                return new ManagerRestaurantPromotionsAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_restaurant_promotions_list_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new ManagerRestaurantPromotionsAdapter.EmptyViewHolder(
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

        @BindView(R.id.manager_promotions_list_item_image_view)
        ImageView promotionImageView;

        @BindView(R.id.manager_promotions_list_item_text_view)
        TextView titleTextView;

        @BindView(R.id.delete_promotion_button)
        ImageButton deleteButton;


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
        public void onBind(final int position) {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.openPromotionDetailsActivity(promotion);
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCallback!=null){
                        mCallback.deletePromotion(promotion,position);
                        //FIXME Milan: Dogovoriti gdje se radi birsanje
                        mRestaurantPromotionsList.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });
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
