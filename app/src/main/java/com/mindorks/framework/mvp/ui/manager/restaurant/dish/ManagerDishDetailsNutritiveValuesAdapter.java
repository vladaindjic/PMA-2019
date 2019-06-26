package com.mindorks.framework.mvp.ui.manager.restaurant.dish;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerDishDetailsNutritiveValuesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private ManagerDishNutitiveValueCallback mCallback;

    private List<DishDetailsResponse.NutritiveValue> mNutritiveValues;

    public ManagerDishDetailsNutritiveValuesAdapter(List<DishDetailsResponse.NutritiveValue> mNutritiveValues) {
        this.mNutritiveValues = mNutritiveValues;
    }

    public ManagerDishNutitiveValueCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(ManagerDishNutitiveValueCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void addItems(List<DishDetailsResponse.NutritiveValue> nutritiveValues) {
        mNutritiveValues.clear();
        mNutritiveValues.addAll(nutritiveValues);
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
                return new ManagerDishDetailsNutritiveValuesAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_dish_details_nutritive_value_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new ManagerDishDetailsNutritiveValuesAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_item, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mNutritiveValues != null && mNutritiveValues.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mNutritiveValues != null && mNutritiveValues.size() > 0) {
            return mNutritiveValues.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.manager_dish_details_nutritive_values_item_txt_name)
        TextView txtViewName;

        @BindView(R.id.manager_dish_details_nutritive_values_item_txt_value)
        TextView txtViewValue;

        @BindView(R.id.manager_dish_details_nutritive_values_item_txt_unit)
        TextView txtViewUnit;

        @BindView(R.id.manager_restaurant_details_remove_nutritive_value_btn)
        ImageButton nutitiveValueDeleteBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            txtViewName.setText("");
            txtViewValue.setText("");
            txtViewUnit.setText("");

        }

        public void onBind(int position) {
            super.onBind(position);

            if (mNutritiveValues == null || mNutritiveValues.size() <= 0) {
                return;
            }

            final DishDetailsResponse.NutritiveValue nutritiveValue = mNutritiveValues.get(position);


            if (nutritiveValue.getName() != null) {
                txtViewName.setText(nutritiveValue.getName());
            }

            if (nutritiveValue.getValue() != null) {
                txtViewValue.setText(String.format(Locale.US, "%.2f", nutritiveValue.getValue()));
            }

            if (nutritiveValue.getUnit() != null) {
                txtViewUnit.setText(nutritiveValue.getUnit());
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

            nutitiveValueDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCallback!=null){
                        mCallback.deleteNutritiveValue(nutritiveValue.getName());
                    }
                }
            });
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.txt_missing)
        TextView missingTxt;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            missingTxt.setText("nutrition value!");
        }
        @Override
        protected void clear() {

        }
    }

}