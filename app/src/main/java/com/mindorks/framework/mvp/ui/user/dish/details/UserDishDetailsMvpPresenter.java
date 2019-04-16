package com.mindorks.framework.mvp.ui.user.dish.details;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserDishDetailsMvpPresenter<V extends UserDishDetailsMvpView>
        extends MvpPresenter<V> {

    public void onViewPrepared(Long dishId);

}
