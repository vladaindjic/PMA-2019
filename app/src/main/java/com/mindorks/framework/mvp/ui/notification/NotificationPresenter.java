package com.mindorks.framework.mvp.ui.notification;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class NotificationPresenter<V extends NotificationMvpView> extends BasePresenter<V>
        implements NotificationMvpPresenter<V> {

    @Inject
    public NotificationPresenter(DataManager dataManager,
                                 SchedulerProvider schedulerProvider,
                                 CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getNotificationsApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<NotificationResponse>() {

                    @Override
                    public void accept(@NonNull NotificationResponse response)
                            throws Exception {
                        System.out.println("OVO JE RESPONSE " + response);
                        if (response != null && response.getData() != null) {
                            System.out.println("*********************Ovde sam");
                            getMvpView().updateNotifications(response.getData().getNotifications());
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
                            System.out.println("********************* " + throwable);
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));

        getMvpView().hideLoading();
    }
}