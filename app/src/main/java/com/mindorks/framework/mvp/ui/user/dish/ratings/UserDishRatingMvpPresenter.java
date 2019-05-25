package com.mindorks.framework.mvp.ui.user.dish.ratings;

import android.widget.RatingBar;

import com.mindorks.framework.mvp.data.network.model.ComentVoteRequest;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserDishRatingMvpPresenter<V extends UserDishRatingMvpView> extends MvpPresenter<V> {

    void onViewPrepared(Long dishId);
    void rateDish(Long id, RatingBar ratingBar);
    void leaveComment(Long id, String text);
    void voteComent(Long id, ComentVoteRequest request);
}
