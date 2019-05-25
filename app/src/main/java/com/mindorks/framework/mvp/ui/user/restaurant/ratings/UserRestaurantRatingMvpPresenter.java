package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import android.widget.RatingBar;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserRestaurantRatingMvpPresenter<V extends UserRestaurantRatingMvpView> extends MvpPresenter<V> {

    public void onViewPrepared(Long restaurantId);
    public void rateRestaurant(Long restaurantId, RatingBar ratingBar);
    void leaveComment(Long restaurantId,String text);

    void likeComment(Long id);
    void dislikeComment(Long id);

}
