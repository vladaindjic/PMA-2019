package com.mindorks.framework.mvp.ui.restaurant.user.grid;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;

public class RestaurantsGridAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Callback callback;

    private List<RestaurantsResponse.Restaurant> mRestaurantsResponseList;

    public RestaurantsGridAdapter(List<RestaurantsResponse.Restaurant> mRestaurantsResponseList) {
        this.mRestaurantsResponseList = mRestaurantsResponseList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface Callback {
        void onBlogEmptyViewRetryClick();
    }


    public RestaurantsGridAdapter.Callback getCallback() {
        return callback;
    }

    public void setCallback(RestaurantsGridAdapter.Callback callback) {
        this.callback = callback;
    }
}
