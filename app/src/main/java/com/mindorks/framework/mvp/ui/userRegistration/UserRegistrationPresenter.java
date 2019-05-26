package com.mindorks.framework.mvp.ui.userRegistration;

import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationRequest;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.CommonUtils;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserRegistrationPresenter<V extends UserRegistrationMvpView> extends BasePresenter<V>
        implements UserRegistrationMvpPresenter<V> {

    private static final String TAG = "UserRegistrationPresenter";

    @Inject
    public UserRegistrationPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void onRegisterUserClick(String email, String username, String name,
                                    String lastname, String password, String repeatedPassword) {

        // validate username
        if (username == null || username.isEmpty()) {
            getMvpView().onError(R.string.empty_username);
            return;
        }
        // validate name
        if (name == null || name.isEmpty()) {
            getMvpView().onError(R.string.empty_name);
            return;
        }
        // validate last name
        if (lastname == null || lastname.isEmpty()) {
            getMvpView().onError(R.string.empty_lastname);
            return;
        }

        //validate email
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        // validate password
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        if (!password.equals(repeatedPassword)) {
            getMvpView().onError(R.string.not_matching_password);
            return;
        }

        getCompositeDisposable().add(getDataManager()
                .doUserRegistrationApiCall(new UserRegistrationRequest(email, username, password,
                        repeatedPassword, name, lastname))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserRegistrationResponse>() {
                    @Override
                    public void accept(UserRegistrationResponse response) throws Exception {
                        // successfully registered user
                        if (response.isSuccesful()) {
                            getMvpView().hideLoading();
                            getMvpView().openLoginActivity();
                            getMvpView().finishRegistration();
                            getMvpView().showMessage("You have been successfully registered. " +
                                    "Please login now.");
                            return;
                        }


                        // show error message
                        getMvpView().showMessage(response.getMessage());

                        if (!isViewAttached()) {
                            return;
                        }


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));

    }

    @Override
    public void onUserLoginTextViewClick() {
        getMvpView().openLoginActivity();
    }
}
