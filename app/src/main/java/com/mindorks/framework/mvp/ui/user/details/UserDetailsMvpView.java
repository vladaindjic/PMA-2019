package com.mindorks.framework.mvp.ui.user.details;

import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

public interface UserDetailsMvpView extends MvpView {

    void updateDetails(UserDetailsResponse.UserDetails details);

}
