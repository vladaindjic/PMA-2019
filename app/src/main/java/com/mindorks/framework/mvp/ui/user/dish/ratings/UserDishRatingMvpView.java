package com.mindorks.framework.mvp.ui.user.dish.ratings;

import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserDishRatingMvpView extends MvpView {


    void updateDishRating(RestaurantRatingResponse.RestaurantRating restaurantRating);
    void updateDishRatingValue(Double score);
}
