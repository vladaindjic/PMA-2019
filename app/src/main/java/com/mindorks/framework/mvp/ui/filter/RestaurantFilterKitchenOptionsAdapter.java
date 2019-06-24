package com.mindorks.framework.mvp.ui.filter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.RestaurantFilterCallback;
import com.mindorks.framework.mvp.utils.AppLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestaurantFilterKitchenOptionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private RestaurantFilterCallback mCallback;
    private List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> mKitchenOptionList;

    public RestaurantFilterKitchenOptionsAdapter(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> mKitchenOptionList) {
        this.mKitchenOptionList = mKitchenOptionList;
    }

    public RestaurantFilterCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(RestaurantFilterCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void addItems(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> kitchenOptionsList) {
        System.out.println("DODAJEM " + kitchenOptionsList.size());
        this.mKitchenOptionList.addAll(kitchenOptionsList);
        notifyDataSetChanged();
    }

    public List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> getmKitchenOptionList() {
        return mKitchenOptionList;
    }

    public void setmKitchenOptionList(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> mKitchenOptionList) {
        this.mKitchenOptionList = mKitchenOptionList;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new RestaurantFilterKitchenOptionsAdapter.ViewHolder(
//                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kitchen_option_view, parent,
//                        false));
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new RestaurantFilterKitchenOptionsAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kitchen_option_view, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new RestaurantFilterKitchenOptionsAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mKitchenOptionList != null && mKitchenOptionList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
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
            }

            if (kitchen.getValue() != null) {
                checkBox.setChecked(kitchen.getValue());
            }

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBox.setChecked(isChecked);
                    kitchen.setValue(isChecked);
                }
            });
        }

    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.btn_retry)
        Button retryButton;

        @BindView(R.id.tv_message)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @OnClick(R.id.btn_retry)
        void onRetryClick() {
            if (mCallback != null)
                mCallback.onRestaurantsEmptyViewRetryClick();
        }
    }
}