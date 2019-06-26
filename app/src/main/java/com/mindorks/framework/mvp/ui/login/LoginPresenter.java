/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.mindorks.framework.mvp.ui.login;

import com.androidnetworking.error.ANError;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.LoginRequest;
import com.mindorks.framework.mvp.data.network.model.LoginResponse;
import com.mindorks.framework.mvp.data.network.model.MyRestaurantsResponse;
import com.mindorks.framework.mvp.data.prefs.AppPreferencesHelper;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.CommonUtils;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by janisharali on 27/01/17.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onServerLoginClick(String email, String password) {
        //validate email and password
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        getDataManager().updateUserInfo(
                                response.getAccessToken(),
                                response.getUserId(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                response.getUserName(),
                                response.getUserEmail(),
                                response.getImgUrl(),
                                response.getUserRole());

                        if (!isViewAttached()) {
                            return;
                        }

                        if (response.getUserRole().equals("USER")) {
                            // da UserRestaurantsActivity odradi pretplatu
                            AppPreferencesHelper.RECENTLY_CHANGED_NOTIFICATION_PREFERENCE = true;

                            getMvpView().hideLoading();

                            // podrazumevana svetla tema
                            getDataManager().setDarkThemeOn(false);
                            // podrazumevano engleski jezik
                            getDataManager().setActiveLanguage("en");
                            // podrazumevano nema ustede podataka
                            getDataManager().setSaveNetworkDataOn(false);
                            // podrazumevano ukljucene notifikacije
                            getDataManager().setNotificationTurnedOn(true);

                            // ponistimo sve restorane koje je korisnik kesirao
                            getCompositeDisposable().add(getDataManager().deleteAllMyRestaurants().subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    // ponistimo sve kuhinje restorana
                                    getDataManager().deleteAllKitchensDB().subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            // setujemo podrazumevani user filter
                                            UserFilter userFilter = new UserFilter();
                                            userFilter.setDistance(33.0);
                                            userFilter.setOpen(false);
                                            userFilter.setDelivery(false);
                                            userFilter.setDailyMenu(false);
                                            getDataManager().saveUserFilter(userFilter).subscribe(new Consumer<Long>() {
                                                @Override
                                                public void accept(Long aLong) throws Exception {
                                                    getDataManager().setActiveUserFilterId(aLong);
                                                    // konacno otvaranje
                                                    getMvpView().openUserRestaurantsActivity();
                                                }
                                            });

                                        }
                                    });
                                }
                            }));

                        } else {
                            getMvpView().hideLoading();
                            // podrazumevana svetla tema
                            getDataManager().setDarkThemeOn(false);
                            // podrazumevano engleski jezik
                            getDataManager().setActiveLanguage("en");
                            // podrazumevano nema ustede podataka
                            getDataManager().setSaveNetworkDataOn(false);
                            // podrazumevano ukljucene notifikacije
                            getDataManager().setNotificationTurnedOn(true);

                            getMvpView().openManagerRestaurantActivity();
                            getDataManager().setRestaurantIdManager(response.getRestaurantId());
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
    public void onUserRegisterTextViewClick() {
        getMvpView().openUserRegistrationActivity();
    }
}
