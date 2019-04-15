package com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private MealListItemCallback mCallback;

    private List<DailyMenuResponse.Meal> mMealList;

    public MealListAdapter(List<DailyMenuResponse.Meal> mMealList) {
        this.mMealList = mMealList;
    }

    public interface MealListItemCallback {
        void openMealActivity(DailyMenuResponse.Meal meal);
    }

    public MealListItemCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(MealListItemCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void addItems(List<DailyMenuResponse.Meal> mealList) {
        mMealList.addAll(mealList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MealListAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_list_item_layout,
                        parent,
                        false));

//        switch (viewType) {
//            case VIEW_TYPE_NORMAL:
//                return new DishListAdapter.ViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_type_list_item_layout, parent,
//                                false));
//            case VIEW_TYPE_EMPTY:
//            default:
//                return new DishListAdapter.EmptyViewHolder(
//                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mMealList != null && mMealList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mMealList != null && mMealList.size() > 0) {
            return mMealList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.txt_meal_name)
        TextView txtMealName;

        @BindView(R.id.txt_meal_description)
        TextView txtMealDescription;

        @BindView(R.id.txt_meal_price)
        TextView txtMealPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            txtMealName.setText("");
            txtMealDescription.setText("");
            txtMealName.setText("");

        }

        public void onBind(int position) {
            super.onBind(position);

            final DailyMenuResponse.Meal meal = mMealList.get(position);

            if (meal.getName() != null) {
                txtMealName.setText(meal.getName());
            }

            if (meal.getDescription() != null) {
                txtMealDescription.setText(meal.getDescription());
            }

            if (meal.getPrice() != null) {
                // FIXME vi3: ovde dodate izabrani locale
                txtMealPrice.setText(String.format(Locale.US,"%.2f", meal.getPrice()));
            }

            /// TODO: open restaurant when click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.openMealActivity(meal);
                    }
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