package com.mindorks.framework.mvp.ui.user.restaurant.menu;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DishListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private BasePresenter basePresenterForImageUrlProviding;

    private DishListItemCallback mCallback;

    private List<MenuResponse.Dish> mDishList;

    public DishListAdapter(List<MenuResponse.Dish> mDishList) {
        this.mDishList = mDishList;
    }

    public interface DishListItemCallback {
        void openDishActivity(MenuResponse.Dish dish);
        void onDishesEmptyViewRetryClick();
    }

    public DishListItemCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(DishListItemCallback mCallback) {
        this.mCallback = mCallback;
    }

    public BasePresenter getBasePresenterForImageUrlProviding() {
        return basePresenterForImageUrlProviding;
    }

    public void setBasePresenterForImageUrlProviding(BasePresenter basePresenterForImageUrlProviding) {
        this.basePresenterForImageUrlProviding = basePresenterForImageUrlProviding;
    }

    public void addItems(List<MenuResponse.Dish> dishList) {
        mDishList.clear();
        mDishList.addAll(dishList);
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
                return new DishListAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_list_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_item, parent, false));
        }
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

        @BindView(R.id.txt_dish_name)
        TextView txtDishName;

        @BindView(R.id.img_dish)
        ImageView imgDish;

        @BindView(R.id.txt_dish_price)
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
                        .load(basePresenterForImageUrlProviding.getImageUrlFor(BasePresenter.ENTITY_DISH,
                                dish.getImgUrl()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
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

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.txt_missing)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            messageTextView.setText( " dish for this category!");
        }

        @Override
        protected void clear() {

        }
    }

}
