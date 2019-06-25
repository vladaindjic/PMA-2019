package com.mindorks.framework.mvp.ui.user.restaurant.menu;

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
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.ui.utils.OnRetryButtonClickCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DishTypeListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private DishTypeItemListCallback mCallback;
    private BasePresenter basePresenterForImageUrlProviding;


    private List<MenuResponse.DishType> mDishTypeList;
    private Context context;

    public DishTypeListAdapter(List<MenuResponse.DishType> mDishTypeList, Context context) {
        this.mDishTypeList = mDishTypeList;
        this.context = context;
    }

    public interface DishTypeItemListCallback extends OnRetryButtonClickCallback {

    }

    public DishTypeItemListCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(DishTypeItemListCallback mCallback) {
        this.mCallback = mCallback;
    }

    public BasePresenter getBasePresenterForImageUrlProviding() {
        return basePresenterForImageUrlProviding;
    }

    public void setBasePresenterForImageUrlProviding(BasePresenter basePresenterForImageUrlProviding) {
        this.basePresenterForImageUrlProviding = basePresenterForImageUrlProviding;
    }

    public void addItems(List<MenuResponse.DishType> dishTypeList) {
        mDishTypeList.clear();
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
                return new DishTypeListAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_type_list_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new DishTypeListAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_item, parent, false));
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

        @BindView(R.id.txt_dish_type_name)
        TextView txtDishTypeName;

        @BindView(R.id.dish_list_recyclerview)
        RecyclerView mRecyclerView;

        // Injektovanje ne prolazi
        @Inject
        LinearLayoutManager mLayoutManager;

        @Inject
        DishListAdapter mDishListAdapter;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            txtDishTypeName.setText("");
            if (mDishListAdapter != null) {
                mDishListAdapter.addItems(new ArrayList<MenuResponse.Dish>());
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
            mDishListAdapter = new DishListAdapter(new ArrayList<MenuResponse.Dish>());
            mDishListAdapter.addItems(dishType.getDishList());
            mDishListAdapter.setBasePresenterForImageUrlProviding(basePresenterForImageUrlProviding);

            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mDishListAdapter);


            if (context instanceof UserRestaurantActivity) {
                mDishListAdapter.setmCallback((UserRestaurantActivity) context);
            }


        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.txt_missing)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            messageTextView.setText( " menu for this restaurant!");
        }

        @Override
        protected void clear() {

        }
    }

}
