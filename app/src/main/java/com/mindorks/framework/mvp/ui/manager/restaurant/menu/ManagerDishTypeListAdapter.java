package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;
import com.mindorks.framework.mvp.ui.utils.OnRetryButtonClickCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerDishTypeListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private ManagerDishTypeItemListCallback mCallback;

    private List<MenuResponse.DishType> mDishTypeList;
    private Context context;

    public ManagerDishTypeListAdapter(List<MenuResponse.DishType> mDishTypeList, Context context) {
        this.mDishTypeList = mDishTypeList;
        this.context = context;
    }

    public interface ManagerDishTypeItemListCallback extends OnRetryButtonClickCallback {

    }

    public ManagerDishTypeItemListCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(ManagerDishTypeItemListCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void addItems(List<MenuResponse.DishType> dishTypeList) {
        mDishTypeList.addAll(dishTypeList);
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
                return new ManagerDishTypeListAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_dish_type_list_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new ManagerDishTypeListAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDishTypeList != null && mDishTypeList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mDishTypeList != null && mDishTypeList.size() > 0) {
            return mDishTypeList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.manager_txt_dish_type_name)
        TextView txtDishTypeName;

        @BindView(R.id.manager_dish_list_recyclerview)
        RecyclerView mRecyclerView;

        // Injektovanje ne prolazi
        @Inject
        LinearLayoutManager mLayoutManager;

        @Inject
        ManagerDishListAdapter mManagerDishListAdapter;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            txtDishTypeName.setText("");
            if (mManagerDishListAdapter != null) {
                mManagerDishListAdapter.addItems(new ArrayList<MenuResponse.Dish>());
            }
        }

        public void onBind(int position) {
            super.onBind(position);

            final MenuResponse.DishType dishType = mDishTypeList.get(position);

            if (dishType.getName() != null) {
                txtDishTypeName.setText(dishType.getName());
            }

            // FIXME vi3: videti da li postoji sansa da se ovo samo injektuje
            // Za sada je moralo ovako da se implementira, jer injekcija ne prolazi
            mManagerDishListAdapter = new ManagerDishListAdapter(new ArrayList<MenuResponse.Dish>());
            mManagerDishListAdapter.addItems(dishType.getDishList());

            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mManagerDishListAdapter);


            if (context instanceof UserRestaurantActivity) {
                mManagerDishListAdapter.setmCallback((ManagerRestaurantActivity) context);
            }


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
                mCallback.onsEmptyViewRetryButtonClick();
        }
    }

}
