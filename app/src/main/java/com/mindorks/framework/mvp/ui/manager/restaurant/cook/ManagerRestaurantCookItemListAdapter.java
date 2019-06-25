package com.mindorks.framework.mvp.ui.manager.restaurant.cook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerRestaurantCookItemListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> mRestaurantCookItemList;
    private ManagerCookItemListCallback mCallback;
    private ManagerCookItemDeleteCallback mDeleteCallback;

    public ManagerRestaurantCookItemListAdapter(ArrayList<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> mRestaurantCookItemList) {
        this.mRestaurantCookItemList = mRestaurantCookItemList;
    }

    public ManagerCookItemListCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(ManagerCookItemListCallback mCallback) {
        this.mCallback = mCallback;
    }

    public ManagerCookItemDeleteCallback getmDeleteCallback() {
        return mDeleteCallback;
    }

    public void setmDeleteCallback(ManagerCookItemDeleteCallback mDeleteCallback) {
        this.mDeleteCallback = mDeleteCallback;
    }

    public void addItems(List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> mRestaurantCookItemList) {
        this.mRestaurantCookItemList.clear();
        this.mRestaurantCookItemList.addAll(mRestaurantCookItemList);
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
                return new ManagerRestaurantCookItemListAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.cook_item_view, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new ManagerRestaurantCookItemListAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_item, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        if (mRestaurantCookItemList != null && mRestaurantCookItemList.size() > 0) {
            return mRestaurantCookItemList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRestaurantCookItemList != null && mRestaurantCookItemList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public interface ManagerCookItemListCallback {
        void openDishDetailsActivity(MenuResponse.Dish dish);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.cook_item_view)
        TextView textView;


        @BindView(R.id.cook_dish_price)
        TextView textPrice;

        @BindView(R.id.cook_item_delete_btn)
        Button removeButton;

        @BindView(R.id.cook_item_image_view)
        ImageView imageView;

//          TODO ovde se slika treba dodati
//        @BindView(R.id.cook_item_image_view)
//        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            textView.setText("");
            textPrice.setText("");
        }

        public void onBind(final int position) {
            super.onBind(position);

            if (mRestaurantCookItemList == null || mRestaurantCookItemList.size() <= 0) {
                return;
            }

            final RestaurantCookResponse.RestaurantCook.RestaurantCookItem restaurantCookItem = mRestaurantCookItemList.get(position);


            if (restaurantCookItem.getName() != null) {
                textView.setText(restaurantCookItem.getName());
                textPrice.setText(String.format("%s", restaurantCookItem.getPrice()));
            }

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRestaurantCookItemList.remove(restaurantCookItem);
                    notifyDataSetChanged();
                }
            });

            if (restaurantCookItem.getImgUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(restaurantCookItem.getImgUrl())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
//                        .asBitmap()
//                        .centerCrop()
                        .into(imageView);
            }

            // TODO PREBACI SE NA KLIK TAMO DE TREBA
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        MenuResponse.Dish dish = new MenuResponse.Dish();
                        dish.setId(restaurantCookItem.getId());
                        mCallback.openDishDetailsActivity(dish);
                    }
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteCallback != null) {
                        mDeleteCallback.deleteDish(restaurantCookItem.getId());
                    }
                }
            });

//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (restaurantCookItem.getRestaurantId() != null) {
//                        if (mCallback != null) {
//                            RestaurantsResponse.Restaurant restaurant =
//                                    new RestaurantsResponse.Restaurant();
//                            restaurant.setId(notification.getRestaurantId());
//                            mCallback.openRestaurantDetailsActivity(restaurant);
//                        }
//                    }
//                }
//            });
        }
    }

    /**
     * EmptyViewHolder
     */
    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.txt_missing)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            messageTextView.setText( " dish!");
        }

        @Override
        protected void clear() {

        }
    }

}