package com.mindorks.framework.mvp.ui.user.settings;

import com.androidnetworking.error.ANError;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.MyRestaurantsResponse;
import com.mindorks.framework.mvp.data.prefs.AppPreferencesHelper;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsMvpView;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserSettingsPresenter<V extends UserSettingsMvpView> extends BasePresenter<V>
        implements UserSettingsMvpPresenter<V> {

    private static final String TAG = "UserSettingsPresenter";

    @Inject
    public UserSettingsPresenter(DataManager dataManager,
                                    SchedulerProvider schedulerProvider,
                                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


}
