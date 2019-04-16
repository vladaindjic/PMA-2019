package com.mindorks.framework.mvp.ui.settings;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SettingsPresenter <V extends SettingsMvpView> extends BasePresenter<V>
        implements SettingsMvpPresenter<V> {

    private static final String TAG = "SettingsPresenter";

    @Inject
    public SettingsPresenter(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getSettingsApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SettingsResponse>() {

                    @Override
                    public void accept(@NonNull SettingsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateGeneralSettingsOptions(response.getData().getSettingsGeneralOptionsList());
                            getMvpView().updateLanguageSettingsOptions(response.getData().getLanguageOptionList());
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