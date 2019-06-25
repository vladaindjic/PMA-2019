package com.mindorks.framework.mvp.ui.user.details.update;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.data.network.model.UpdateUserDetailsRequest;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserDetailsUpdatePresenter<V extends UserDetailsUpdateMvpView> extends BasePresenter<V>
        implements UserDetailsUpdateMvpPresenter<V> {
    private static final String TAG = "UserDishPresenter";

    @Inject
    public UserDetailsUpdatePresenter(DataManager dataManager,
                                      SchedulerProvider schedulerProvider,
                                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long Id) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getUserDetailsApiCall(Id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserDetailsResponse>() {
                    @Override
                    public void accept(@NonNull UserDetailsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateDetails(response.getData());
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
    public void submitUserImage(byte[] imgBytes, final UpdateUserDetailsRequest request) {
        getCompositeDisposable().add(getDataManager()
                .putUserImageUpdateRaw(imgBytes)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserDetailsResponse>() {
                    @Override
                    public void accept(@NonNull UserDetailsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            // uspesno postavljan slika, izlogujemo se
                            updateUserDetails(request);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }
                        // ako ne uspe upload slike, onda cemo ostaviti menadzera na ovoj
                        // aktivnosti
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
