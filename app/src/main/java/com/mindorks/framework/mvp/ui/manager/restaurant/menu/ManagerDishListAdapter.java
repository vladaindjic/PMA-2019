package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerDishListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private ManagerDishListItemCallback mCallback;

    private List<MenuResponse.Dish> mDishList;

    public ManagerDishListAdapter(List<MenuResponse.Dish> mDishList) {
        this.mDishList = mDishList;
    }

    public interface ManagerDishListItemCallback {
        void openDishActivity(MenuResponse.Dish dish);
    }

    public ManagerDishListItemCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(ManagerDishListItemCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void addItems(List<MenuResponse.Dish> dishList) {
        mDishList.addAll(dishList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ManagerDishListAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_dish_list_item_layout, parent,
                        false));

//        switch (viewType) {
//            case VIEW_TYPE_NORMAL:
//                return new ManagerDishListAdapter.ViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_type_list_item_layout, parent,
//                                false));
//            case VIEW_TYPE_EMPTY:
//            default:
//                return new ManagerDishListAdapter.EmptyViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDishList != null && mDishList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mDishList != null && mDishList.size() > 0) {
            return mDishList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.manager_txt_dish_name)
        TextView txtDishName;

        @BindView(R.id.manager_img_dish)
        ImageView imgDish;

        @BindView(R.id.manager_txt_dish_price)
        TextView txtDishPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            txtDishName.setText("");
            imgDish.setImageBitmap(null);
            txtDishPrice.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final MenuResponse.Dish dish = mDishList.get(position);

            if (dish.getName() != null) {
                txtDishName.setText(dish.getName());
            }

            if (dish.getImgUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(dish.getImgUrl())
                        .into(imgDish);
            }

            if (dish.getPrice() != null) {
                // FIXME vi3: ovde dodate izabrani locale
                txtDishPrice.setText(String.format(Locale.US,"%.2f", dish.getPrice()));
            }

            /// TODO: open restaurant when click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.openDishActivity(dish);
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