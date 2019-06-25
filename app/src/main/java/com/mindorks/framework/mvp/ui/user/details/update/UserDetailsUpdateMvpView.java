package com.mindorks.framework.mvp.ui.user.details.update;

import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserDetailsUpdateMvpView extends MvpView {
    void updateDetails(UserDetailsResponse.UserDetails details);

    void back();

    byte[] getImgBytes();

    void openLoginActivity();

}
