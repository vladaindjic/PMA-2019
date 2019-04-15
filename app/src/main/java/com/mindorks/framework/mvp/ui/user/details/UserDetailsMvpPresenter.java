package com.mindorks.framework.mvp.ui.user.details;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserDetailsMvpPresenter<V extends UserDetailsMvpView>
        extends MvpPresenter<V> {

    public void onViewPrepared(Long Id);

}
