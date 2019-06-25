package com.mindorks.framework.mvp.ui.user.details.update;

import com.mindorks.framework.mvp.data.network.model.UpdateUserDetailsRequest;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

import java.io.File;

public interface UserDetailsUpdateMvpPresenter<V extends UserDetailsUpdateMvpView> extends MvpPresenter<V> {

    void onViewPrepared(Long Id);

    void updateUserDetails(UpdateUserDetailsRequest request);

    void submitUserImage(byte[] imgBytes, UpdateUserDetailsRequest request);

}
