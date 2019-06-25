package com.mindorks.framework.mvp.ui.user.details.password;

import com.mindorks.framework.mvp.data.network.model.ChangePasswordRequest;
import com.mindorks.framework.mvp.data.network.model.UpdateUserDetailsRequest;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface UserDetailsPasswordMvpPresenter<V extends UserDetailsPasswordMvpView> extends MvpPresenter<V> {

    void updateUserDetails(UpdateUserDetailsRequest request);

    void changePassword(ChangePasswordRequest request);

}
