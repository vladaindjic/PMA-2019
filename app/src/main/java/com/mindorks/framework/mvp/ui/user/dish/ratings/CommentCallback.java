package com.mindorks.framework.mvp.ui.user.dish.ratings;

public interface CommentCallback {
    void voteUp(Long id);
    void voteDown(Long id);
}
