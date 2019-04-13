package com.mindorks.framework.mvp.ui.filter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.utils.AppLogger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantFilterKitchenOptionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> mKitchenOptionList;

    public RestaurantFilterKitchenOptionsAdapter(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> mKitchenOptionList) {
        this.mKitchenOptionList = mKitchenOptionList;
    }

    public void addItems(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> kitchenOptionsList) {
        mKitchenOptionList.addAll(kitchenOptionsList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RestaurantFilterKitchenOptionsAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kitchen_option_view, parent,
                        false));

    }

    @Override
    public int getItemCount() {
        if (mKitchenOptionList != null && mKitchenOptionList.size() > 0) {
            return mKitchenOptionList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.kitchenOptionCeckBox)
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            checkBox.setText("");
            checkBox.setChecked(false);
        }

        public void onBind(int position) {
            super.onBind(position);

            if (mKitchenOptionList == null || mKitchenOptionList.size() <= 0) {
                return;
            }

            final RestaurantFilterResponse.RestaurantFilter.KitchenOptions kitchen = mKitchenOptionList.get(position);


            if (kitchen.getName() != null) {
                checkBox.setText(kitchen.getName());
                checkBox.setChecked(kitchen.getValue());
            }

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kitchen.getName() != null) {
                        try {
                            checkBox.setChecked(!checkBox.isChecked());
                        } catch (Exception e) {
                            AppLogger.d("url error");
                        }
                    }
                }
            });
        }

    }
}