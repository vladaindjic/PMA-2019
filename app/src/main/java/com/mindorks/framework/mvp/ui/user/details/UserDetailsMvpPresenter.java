package com.mindorks.framework.mvp.ui.user.details;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

import java.io.File;

public interface UserDetailsMvpPresenter<V extends UserDetailsMvpView>
        extends MvpPresenter<V> {

    void onViewPrepared(Long Id);
    void uploadImageBytes(File imageBytes);


}
