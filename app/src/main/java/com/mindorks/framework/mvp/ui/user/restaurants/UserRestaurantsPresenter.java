package com.mindorks.framework.mvp.ui.user.restaurants;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.LogoutResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserRestaurantsPresenter<V extends UserRestaurantsMvpView> extends BasePresenter<V>
        implements UserRestaurantsMvpPresenter<V> {

    private static final String TAG = "UserRestaurantsPresenter";

    @Inject
    public UserRestaurantsPresenter(DataManager dataManager,
                                    SchedulerProvider schedulerProvider,
                                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onDrawerOptionAboutClick() {
        getMvpView().closeNavigationDrawer();
        getMvpView().showAboutFragment();
    }

    @Override
    public void onDrawerOptionLogoutClick() {
        getMvpView().showLoading();
        getDataManager().setUserAsLoggedOut();
        getMvpView().hideLoading();
        getMvpView().openLoginActivity();


//        getMvpView().showLoading();dovla
//
//        getCompositeDisposable().add(getDataManager().doLogoutApiCall()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Consumer<LogoutResponse>() {
//                    @Override
//                    public void accept(LogoutResponse response) throws Exception {
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getDataManager().setUserAsLoggedOut();
//                        getMvpView().hideLoading();
//                        getMvpView().openLoginActivity();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        if (!isViewAttached()) {
//                            return;
//                        }
//
//                        getMvpView().hideLoading();
//
//                        // handle the login error here
//                        if (throwable instanceof ANError) {
//                            ANError anError = (ANError) throwable;
//                            handleApiError(anError);
//                        }
//                    }
//                }));

    }

    @Override
    public void onNavMenuCreated() {
        if (!isViewAttached()) {
            return;
        }

        final String currentUserName = getDataManager().getCurrentUserName();
        if (currentUserName != null && !currentUserName.isEmpty()) {
            getMvpView().updateUserName(currentUserName);
        }

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
            getMvpView().updateUserEmail(currentUserEmail);
        }

        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
        if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
            getMvpView().updateUserProfilePic(profilePicUrl);
        }
    }

    @Override
    public void onDrawerMyNotificationsClick() {
        getMvpView().closeNavigationDrawer();
        getMvpView().openNotificationsActivity();
    }

    @Override
    public void onDrawerRestaurantsClick() {
        getMvpView().openRestaurantsActivity();

    }

    @Override
    public void onDrawerMyRestaurantsClick() {
        getMvpView().openMyRestaurantsActivity();

    }

    @Override
    public void onDrawerMyProfileClick() {
        getMvpView().openMyProfileActivity();

    }

    @Override
    public void onDrawerMySettingsClick() {
        getMvpView().openSettingsActivity();

    }


}
