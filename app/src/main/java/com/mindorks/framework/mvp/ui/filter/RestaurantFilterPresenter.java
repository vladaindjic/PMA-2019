package com.mindorks.framework.mvp.ui.filter;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.AllKitchensResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class RestaurantFilterPresenter<V extends RestaurantFilterMvpView> extends BasePresenter<V>
        implements RestaurantFilterMvpPresenter<V> {

    private static final String TAG = "RestaurantFilterPresenter";

    @Inject
    public RestaurantFilterPresenter(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getAllKitchensApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<AllKitchensResponse>() {

                    @Override
                    public void accept(@NonNull AllKitchensResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateKitchenOptions(response.getData());
//                            getMvpView().updateRestaurantFilterOptions(response.getData().getRestaurantFilterOptions());
//                            getMvpView().updateDistance(response.getData().getDistance());
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

    public void readUserFilter() {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getUserFilter(getDataManager().getActiveUserFilterId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserFilter>() {

                    @Override
                    public void accept(@NonNull UserFilter userFilter)
                            throws Exception {
                        if (userFilter != null) {
                            getMvpView().updateUserFilter(userFilter);
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