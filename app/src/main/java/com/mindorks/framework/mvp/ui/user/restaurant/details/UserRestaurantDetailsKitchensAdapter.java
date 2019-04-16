package com.mindorks.framework.mvp.ui.user.restaurant.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRestaurantDetailsKitchensAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


//    private UserRestaurantsCallback mCallback;

    private List<RestaurantDetailsResponse.Kitchen> mKitchenList;

    public UserRestaurantDetailsKitchensAdapter(List<RestaurantDetailsResponse.Kitchen> mKitchenList) {
        this.mKitchenList = mKitchenList;
    }

//    public UserRestaurantsCallback getmCallback() {
//        return mCallback;
//    }
//
//    public void setmCallback(UserRestaurantsCallback mCallback) {
//        this.mCallback = mCallback;
//    }

    public void addItems(List<RestaurantDetailsResponse.Kitchen> kitchenList) {
        mKitchenList.addAll(kitchenList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserRestaurantDetailsKitchensAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.user_restaurant_details_kitchen_item_layout, parent,
                        false));

//        switch (viewType) {
//            case VIEW_TYPE_NORMAL:
//                return new UserDishDetailsKitchensAdapter.ViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurants_list_item_layout, parent,
//                                false));
//            case VIEW_TYPE_EMPTY:
//            default:
//                return new UserDishDetailsKitchensAdapter.EmptyViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mKitchenList != null && mKitchenList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mKitchenList != null && mKitchenList.size() > 0) {
            return mKitchenList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.user_restaurant_details_kitchen_item_txt)
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            titleTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            if (mKitchenList == null || mKitchenList.size() <= 0) {
                return;
            }

            final RestaurantDetailsResponse.Kitchen kitchen = mKitchenList.get(position);


            if (kitchen.getName() != null) {
                titleTextView.setText(kitchen.getName());
            }

            /// TODO: open restaurant when click
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mCallback != null) {
//                        mCallback.openRestaurantDetailsActivity(kitchen);
//                    }
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
//                }
//            });
        }
    }

//    public class EmptyViewHolder extends BaseViewHolder {
//
//        @BindView(R.id.btn_retry)
//        Button retryButton;
//
//        @BindView(R.id.tv_message)
//        TextView messageTextView;
//
//        public EmptyViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//
//        @Override
//        protected void clear() {
//
//        }
//
//        @OnClick(R.id.btn_retry)
//        void onRetryClick() {
//            if (mCallback != null)
//                mCallback.onRestaurantsEmptyViewRetryClick();
//        }
//    }

}
