package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface UserRestaurantRatingMvpView extends MvpView {

    void updateRestaurantCommentList(List<String> comments);
}
