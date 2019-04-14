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

        getCompositeDisposable().add(getDataManager().doLogoutApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LogoutResponse>() {
                    @Override
                    public void accept(LogoutResponse response) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getDataManager().setUserAsLoggedOut();
                        getMvpView().hideLoading();
                        getMvpView().openLoginActivity();
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
    public void onNavMenuCreated() {
        if (!isViewAttached()) {
            return;
        }
        //final String currentUserName = getDataManager().getCurrentUserName();
        final String currentUserName = "BOGOSAV";
        if (currentUserName != null && !currentUserName.isEmpty()) {
            getMvpView().updateUserName(currentUserName);
        }

//        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        final String currentUserEmail = "BOGOSAVIC";
        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
            getMvpView().updateUserEmail(currentUserEmail);
        }

//        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
        final String profilePicUrl = "https://www.google.rs/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiV0enQ5M_hAhUSyKQKHUiYC-EQjRx6BAgBEAU&url=https%3A%2F%2Fgfycat.com%2Fminorkeenibis&psig=AOvVaw15Er7zclR1vkMs9nZvj4Fb&ust=1555338197637009";
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
