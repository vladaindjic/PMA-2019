package com.mindorks.framework.mvp.ui.userRegistration;

import com.mindorks.framework.mvp.di.PerActivity;
import com.mindorks.framework.mvp.ui.base.MvpPresenter;

@PerActivity
public interface UserRegistrationMvpPresenter<V extends UserRegistrationMvpView> extends MvpPresenter<V> {
    void onRegisterUserClick(String email, String username, String name,
                             String lastname, String password, String repeatedPassword);
}
