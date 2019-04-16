package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRestaurantCommentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private List<String> mRestaurantCommentList;


    public UserRestaurantCommentAdapter(List<String> mRestaurantCommentList) {
        this.mRestaurantCommentList = mRestaurantCommentList;
    }

    public void addItems(List<String> commentList) {
        mRestaurantCommentList.addAll(commentList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new UserRestaurantCommentAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new UserRestaurantCommentAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mRestaurantCommentList != null && mRestaurantCommentList.size() > 0) {
            return mRestaurantCommentList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRestaurantCommentList != null && mRestaurantCommentList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    /**
     * ViewHolder
     * Klasa koja definise kako izgleda item pojedinacnog komentara
     */
    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.restaurant_comment_text_view)
        TextView commentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
            commentTextView.setText("");
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            commentTextView.setMovementMethod( new ScrollingMovementMethod());
            final String comment = mRestaurantCommentList.get(position);

//            if (promotion.getImageUrl() != null) {
//                Glide.with(itemView.getContext())
//                        .load(promotion.getImageUrl())
////                        .asBitmap()
////                        .centerCrop()
//                        .into(promotionImageView);
//            }

            if (comment != null) {
                commentTextView.setText(comment);
            }

        }
    }


    /**
     * EmptyViewHolder
     */
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
//            if (mCallback != null)
//                mCallback.onRestaurantsEmptyViewRetryClick();
        }
    }

}
