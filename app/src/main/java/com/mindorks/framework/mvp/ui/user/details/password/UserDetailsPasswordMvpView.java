package com.mindorks.framework.mvp.ui.user.details.password;

import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserDetailsPasswordMvpView extends MvpView {

    void back();
    void changePassword();
    void openLoginActivity();

}
