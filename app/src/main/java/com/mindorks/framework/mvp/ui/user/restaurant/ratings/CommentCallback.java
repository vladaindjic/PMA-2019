package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

public interface CommentCallback {
    void voteUp(Long id);
    void voteDown(Long id);
}
