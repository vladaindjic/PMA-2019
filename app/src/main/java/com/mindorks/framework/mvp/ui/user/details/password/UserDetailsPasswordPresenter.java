package com.mindorks.framework.mvp.ui.user.details.password;

import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.ChangePasswordRequest;
import com.mindorks.framework.mvp.data.network.model.UpdateUserDetailsRequest;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserDetailsPasswordPresenter<V extends UserDetailsPasswordMvpView> extends BasePresenter<V>
        implements UserDetailsPasswordMvpPresenter<V> {
    private static final String TAG = "UserDishPresenter";

    @Inject
    public UserDetailsPasswordPresenter(DataManager dataManager,
                                        SchedulerProvider schedulerProvider,
                                        CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void updateUserDetails(UpdateUserDetailsRequest request) {
        getCompositeDisposable().add(getDataManager()
                .putUserDetailsUpdate(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserDetailsResponse>() {
                    @Override
                    public void accept(UserDetailsResponse response) throws Exception {
                        if (response != null && response.getData() != null) {
                            getDataManager().setUserAsLoggedOut();
                            getMvpView().hideLoading();
                            getMvpView().openLoginActivity();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().hideLoading();

                    }
                }));
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .putUserDetailsPassword(request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserDetailsResponse>() {
                    @Override
                    public void accept(UserDetailsResponse response) throws Exception {
                        if (response != null && response.getHttpStatusCode() == 200 &&
                                response.getData() != null) {
                            getDataManager().setUserAsLoggedOut();
                            getMvpView().hideLoading();
                            getMvpView().openLoginActivity();
                        } else {
                            // TODO vi3 sutra: prikazati gresku kada ne moze sifra da se promeni
                            getMvpView().hideLoading();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().hideLoading();
                    }
                }));
    }
}
