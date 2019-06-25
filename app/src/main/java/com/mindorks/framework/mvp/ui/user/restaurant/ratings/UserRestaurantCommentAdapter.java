package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRestaurantCommentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private List<RestaurantRatingResponse.RestaurantRating.Comment> mRestaurantCommentList;
    private Context myContext;
    private CommentCallback mCallback;

    public UserRestaurantCommentAdapter(List<RestaurantRatingResponse.RestaurantRating.Comment> mRestaurantCommentList) {
        this.mRestaurantCommentList = mRestaurantCommentList;
    }

    public void addItems(List<RestaurantRatingResponse.RestaurantRating.Comment> commentList) {
        mRestaurantCommentList.clear();
        System.out.println("size: " + commentList.size());
        mRestaurantCommentList.addAll(commentList);
        notifyDataSetChanged();
    }

    public void setMyContext(Context myContext) {
        this.myContext = myContext;
    }

    public CommentCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(CommentCallback mCallback) {
        this.mCallback = mCallback;
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
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_item, parent, false));
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

        @BindView(R.id.comment_positive_vote_text)
        TextView commentPositiveCount;

        @BindView(R.id.comment_negative_vote_text)
        TextView commentNegativeCount;

        @BindView(R.id.comment_vote_positive)
        Button votePositive;

        @BindView(R.id.comment_vote_negative)
        Button voteNegative;


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

            commentTextView.setMovementMethod(new ScrollingMovementMethod());
            final RestaurantRatingResponse.RestaurantRating.Comment comment = mRestaurantCommentList.get(position);

            if (comment != null) {
                commentTextView.setText(comment.getText());
            }

            if (comment != null) {
                commentPositiveCount.setText(String.valueOf(comment.getPositive_votes()));
            }

            if (comment != null) {
                commentNegativeCount.setText(String.valueOf(comment.getNegativeVotes()));
            }


            if (comment.getMyVote().equals("POSITIVE")) {
                votePositive.setBackgroundResource(R.drawable.ic_like_blue);
                votePositive.setEnabled(false);
                voteNegative.setEnabled(false);
            }
            if (comment.getMyVote().equals("NEGATIVE")) {
                voteNegative.setBackgroundResource(R.drawable.ic_dislike_red);
                votePositive.setEnabled(false);
                voteNegative.setEnabled(false);
            }

            votePositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(myContext, "Positive", Toast.LENGTH_SHORT).show();
                    if(mCallback!=null) {
                        mCallback.voteUp(comment.getId());
                    }
                }
            });


            voteNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(myContext, "Negative", Toast.LENGTH_SHORT).show();
                    if(mCallback!=null) {
                        mCallback.voteDown(comment.getId());
                    }
                }
            });

        }
    }


    /**
     * EmptyViewHolder
     */
    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.txt_missing)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.messageTextView.setText(" comments for this restaurant!");
        }

        @Override
        protected void clear() {

        }
    }

}
