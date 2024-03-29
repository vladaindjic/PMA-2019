package com.mindorks.framework.mvp.ui.manager.restaurant.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.manager.restaurant.utils.ManagerEmptyViewHolderTextViewOnly;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerRestaurantDetailsKitchensAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


//    private UserRestaurantsCallback mCallback;

    private List<RestaurantDetailsResponse.Kitchen> mKitchenList;

    public ManagerRestaurantDetailsKitchensAdapter(List<RestaurantDetailsResponse.Kitchen> mKitchenList) {
        this.mKitchenList = mKitchenList;
    }

    public interface ManagerRestaurantDetailsKitchensAdapterCallback {
        public void removeKitchenFromRestaurantDetails(RestaurantDetailsResponse.Kitchen kitchen);
    }

    private ManagerRestaurantDetailsKitchensAdapterCallback mCallback;

    public ManagerRestaurantDetailsKitchensAdapterCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(ManagerRestaurantDetailsKitchensAdapterCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void addItems(List<RestaurantDetailsResponse.Kitchen> kitchenList) {
        mKitchenList.clear();
        mKitchenList.addAll(kitchenList);
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
                return new ManagerRestaurantDetailsKitchensAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_restaurant_details_kitchen_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new ManagerEmptyViewHolderTextViewOnly(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_empty_view_item_text_view_only
                                , parent, false), "No Kitchens Selected");
        }
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

        @BindView(R.id.manager_restaurant_details_kitchen_item_txt)
        TextView titleTextView;

        @BindView(R.id.manager_restaurant_details_remove_kitchen_btn)
        ImageButton btnRemoveKitchen;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            titleTextView.setText("");
        }

        public void onBind(final int position) {
            super.onBind(position);

            if (mKitchenList == null || mKitchenList.size() <= 0) {
                return;
            }

            final RestaurantDetailsResponse.Kitchen kitchen = mKitchenList.get(position);


            if (kitchen.getName() != null) {
                titleTextView.setText(kitchen.getName());
            }

            btnRemoveKitchen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // uklanjamo kuhinju sa pozicije koja je prosledjena
                    if (mCallback != null) {
                        mCallback.removeKitchenFromRestaurantDetails(kitchen);
                    }
                }
            });

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
////            if (mCallback != null)
////                mCallback.onRestaurantsEmptyViewRetryClick();
//        }
//    }

}
