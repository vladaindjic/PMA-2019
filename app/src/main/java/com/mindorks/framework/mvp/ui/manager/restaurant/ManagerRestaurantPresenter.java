package com.mindorks.framework.mvp.ui.manager.restaurant;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ManagerRestaurantPresenter<V extends ManagerRestaurantMvpView> extends BasePresenter<V>
        implements ManagerRestaurantMvpPresenter<V> {
    private static final String TAG = "UserDishPresenter";

    @Inject
    public ManagerRestaurantPresenter(DataManager dataManager,
                                      SchedulerProvider schedulerProvider,
                                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onNavMenuCreated() {
        if (!isViewAttached()) {
            return;
        }

//        final String currentUserName = getDataManager().getCurrentUserName();
//        if (currentUserName != null && !currentUserName.isEmpty()) {
//            getMvpView().updateUserName(currentUserName);
//        }
//
//        final String currentUserEmail = getDataManager().getCurrentUserEmail();
//        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
//            getMvpView().updateUserEmail(currentUserEmail);
//        }
//
//        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
//        if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
//            getMvpView().updateUserProfilePic(profilePicUrl);
//        }

    }

    @Override
    public void onDrawerOptionLogoutClick() {
        getMvpView().showLoading();
        getDataManager().setUserAsLoggedOut();
        getMvpView().hideLoading();
        getMvpView().openLoginActivity();
    }
}
