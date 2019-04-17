package com.mindorks.framework.mvp.ui.manager.restaurant.cook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerRestaurantCookItemListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> mRestaurantCookItemList;


    public ManagerRestaurantCookItemListAdapter(ArrayList<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> mRestaurantCookItemList) {
        this.mRestaurantCookItemList = mRestaurantCookItemList;
    }

    public void addItems(List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> mRestaurantCookItemList) {
        this.mRestaurantCookItemList.addAll(mRestaurantCookItemList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ManagerRestaurantCookItemListAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.cook_item_view, parent,
                        false));
    }

    @Override
    public int getItemCount() {
        if (mRestaurantCookItemList != null && mRestaurantCookItemList.size() > 0) {
            return mRestaurantCookItemList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.cook_item_view)
        TextView textView;


        @BindView(R.id.cook_dish_price)
        TextView textPrice;

        @BindView(R.id.cook_item_delete_btn)
        Button removeButton;

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


            if (restaurantCookItem.getRestaurantCookItemData() != null) {
                textView.setText(restaurantCookItem.getRestaurantCookItemData());
                textPrice.setText(String.format("%s", restaurantCookItem.getPrice()));
            }

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRestaurantCookItemList.remove(restaurantCookItem);
                    notifyDataSetChanged();
                }
            });

            // TODO PREBACI SE NA KLIK TAMO DE TREBA
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

}