package com.mindorks.framework.mvp.ui.user.restaurants;

import com.androidnetworking.error.ANError;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.LogoutResponse;
import com.mindorks.framework.mvp.data.network.model.MyRestaurantsResponse;
import com.mindorks.framework.mvp.data.prefs.AppPreferencesHelper;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
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

        // ponistiti pretplate, ukoliko ih ima
        if (getDataManager().isNotificationsTurnedOn()) {
            getCompositeDisposable().add(
                    getDataManager().getSubscriptionsApiCall()
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(new Consumer<MyRestaurantsResponse>() {
                                @Override
                                public void accept(@NonNull MyRestaurantsResponse response)
                                        throws Exception {
                                    if (response != null && response.getData() != null) {
                                        for (MyRestaurantsResponse.MyRestaurant mr :
                                                response.getData()) {
                                            FirebaseMessaging.getInstance().unsubscribeFromTopic(
                                                    "RESTAURANT-" + mr.getId());
                                        }
                                    }
                                    innerLogoutUser();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable)
                                        throws Exception {
                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    innerLogoutUser();
                                    // handle the error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                    }
                                }
                            }));
        } else {
            // u suprotnom, samo odjaviti usera
            innerLogoutUser();
        }

    }

    private void innerLogoutUser() {
        // podrazumevana svetla tema
        getDataManager().setDarkThemeOn(false);
        // podrazumevano engleski jezik
        getDataManager().setActiveLanguage("sr");
        // podrazumevano nema ustede podataka
        getDataManager().setSaveNetworkDataOn(false);
        // podrazumevano ukljucene notifikacije
        getDataManager().setNotificationTurnedOn(true);

        getDataManager().setUserAsLoggedOut();
        getMvpView().hideLoading();
        getMvpView().openLoginActivity();
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

    @Override
    public void checkIfSubscriptionOptionChange() {
        if (AppPreferencesHelper.RECENTLY_CHANGED_NOTIFICATION_PREFERENCE) {
            // oznacavamo da smo ishendlali sta je trebalo
            AppPreferencesHelper.RECENTLY_CHANGED_NOTIFICATION_PREFERENCE = false;

            if (getDataManager().isNotificationsTurnedOn()) {
                getCompositeDisposable().add(
                        getDataManager().getSubscriptionsApiCall()
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(new Consumer<MyRestaurantsResponse>() {
                                    @Override
                                    public void accept(@NonNull MyRestaurantsResponse response)
                                            throws Exception {
                                        if (response != null && response.getData() != null) {
                                            for (MyRestaurantsResponse.MyRestaurant mr :
                                                    response.getData()) {
                                                FirebaseMessaging.getInstance().subscribeToTopic(
                                                        "RESTAURANT-" + mr.getId());
                                            }
                                        }
                                        getMvpView().hideLoading();
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable)
                                            throws Exception {
                                        if (!isViewAttached()) {
                                            return;
                                        }

                                        getMvpView().hideLoading();

                                        // handle the error here
                                        if (throwable instanceof ANError) {
                                            ANError anError = (ANError) throwable;
                                            handleApiError(anError);
                                        }
                                    }
                                }));

            } else {
                // ponistavanje pretplate
                // sada treba odraditi prijavu na topic-e za sve restorane
                getCompositeDisposable().add(
                        getDataManager().getSubscriptionsApiCall()
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(new Consumer<MyRestaurantsResponse>() {
                                    @Override
                                    public void accept(@NonNull MyRestaurantsResponse response)
                                            throws Exception {
                                        if (response != null && response.getData() != null) {
                                            for (MyRestaurantsResponse.MyRestaurant mr :
                                                    response.getData()) {
                                                FirebaseMessaging.getInstance().unsubscribeFromTopic(
                                                        "RESTAURANT-" + mr.getId());
                                            }
                                        }
                                        getMvpView().hideLoading();
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable)
                                            throws Exception {
                                        if (!isViewAttached()) {
                                            return;
                                        }

                                        getMvpView().hideLoading();

                                        // handle the error here
                                        if (throwable instanceof ANError) {
                                            ANError anError = (ANError) throwable;
                                            handleApiError(anError);
                                        }
                                    }
                                }));
            }

        }
    }
}
