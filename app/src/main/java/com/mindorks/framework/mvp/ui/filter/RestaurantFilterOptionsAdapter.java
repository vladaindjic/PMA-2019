package com.mindorks.framework.mvp.ui.filter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.utils.AppLogger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantFilterOptionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<RestaurantFilterResponse.RestaurantFilter.RestaurantFilterOptions> mRestaurantFilterOptionsList;

    public RestaurantFilterOptionsAdapter(List<RestaurantFilterResponse.RestaurantFilter.RestaurantFilterOptions> mRestaurantFilterOptionsList) {
        this.mRestaurantFilterOptionsList = mRestaurantFilterOptionsList;
    }

    public void addItems(List<RestaurantFilterResponse.RestaurantFilter.RestaurantFilterOptions> restaurantFilterOptions) {
        mRestaurantFilterOptionsList.addAll(restaurantFilterOptions);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantFilterOptionsAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_option_view, parent,
                        false));

    }

    @Override
    public int getItemCount() {
        if (mRestaurantFilterOptionsList != null && mRestaurantFilterOptionsList.size() > 0) {
            return mRestaurantFilterOptionsList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.restaurantOptionSwitch)
        Switch optionSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            optionSwitch.setText("");
            optionSwitch.setChecked(false);
        }

        public void onBind(int position) {
            super.onBind(position);

            if (mRestaurantFilterOptionsList == null || mRestaurantFilterOptionsList.size() <= 0) {
                return;
            }

            final RestaurantFilterResponse.RestaurantFilter.RestaurantFilterOptions kitchen = mRestaurantFilterOptionsList.get(position);


            if (kitchen.getName() != null) {
                optionSwitch.setText(kitchen.getName());
                optionSwitch.setChecked(kitchen.getValue());
            }

        }

    }
}
