package com.mindorks.framework.mvp.ui.user.restaurants.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantsListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private UserRestaurantsCallback mCallback;
    private BasePresenter basePresenterForImageUrlProviding;

    private List<RestaurantsResponse.Restaurant> mRestaurantsResponseList;

    public RestaurantsListAdapter(List<RestaurantsResponse.Restaurant> mRestaurantsResponseList) {
        this.mRestaurantsResponseList = mRestaurantsResponseList;
    }

    public UserRestaurantsCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(UserRestaurantsCallback mCallback) {
        this.mCallback = mCallback;
    }

    public BasePresenter getBasePresenterForImageUrlProviding() {
        return basePresenterForImageUrlProviding;
    }

    public void setBasePresenterForImageUrlProviding(BasePresenter basePresenterForImageUrlProviding) {
        this.basePresenterForImageUrlProviding = basePresenterForImageUrlProviding;
    }

    public void addItems(List<RestaurantsResponse.Restaurant> restaurantList) {
        mRestaurantsResponseList.addAll(restaurantList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new RestaurantsListAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurants_list_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new RestaurantsListAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRestaurantsResponseList != null && mRestaurantsResponseList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mRestaurantsResponseList != null && mRestaurantsResponseList.size() > 0) {
            return mRestaurantsResponseList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.restaurants_list_item_image_view)
        ImageView coverImageView;

        @BindView(R.id.restaurants_list_item_txt)
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            titleTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final RestaurantsResponse.Restaurant restaurant = mRestaurantsResponseList.get(position);

            String imgUrl = restaurant.getImageUrl();

            Glide.with(itemView.getContext())
                    .load(basePresenterForImageUrlProviding.getImageUrlFor(BasePresenter.ENTITY_RESTAURANT, restaurant.getImageUrl()))
//                        .asBitmap()
//                        .centerCrop()
                    .into(coverImageView);

            if (restaurant.getName() != null) {
                titleTextView.setText(restaurant.getName());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.openRestaurantDetailsActivity(restaurant);
                    }
//                    if (restaurant.getBlogUrl() != null) {
//                        try {
//                            Intent intent = new Intent();
//                            intent.setAction(Intent.ACTION_VIEW);
//                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                            intent.setData(Uri.parse(restaurant.getBlogUrl()));
//                            itemView.getContext().startActivity(intent);
//                        } catch (Exception e) {
//                            AppLogger.d("url error");
//                        }
//                    }
                }
            });
        }
    }

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
                mCallback.onsEmptyViewRetryButtonClick();
        }
    }

}
