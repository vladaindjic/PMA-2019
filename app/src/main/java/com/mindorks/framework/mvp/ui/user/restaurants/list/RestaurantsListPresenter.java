package com.mindorks.framework.mvp.ui.user.restaurants.list;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class RestaurantsListPresenter<V extends RestaurantsListMvpView> extends BasePresenter<V>
        implements RestaurantsListMvpPresenter<V> {

    @Inject
    public RestaurantsListPresenter(DataManager dataManager,
                                    SchedulerProvider schedulerProvider,
                                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);


    }

    @Override
    public void onViewPrepared(int whatToPrepare) {
        getMvpView().showLoading();

        DataManager dataManager = getDataManager();

        Single<RestaurantsResponse> restaurantsResponseSingle;
        switch (whatToPrepare) {
            case PREPARE_ALL_RESTAURANTS:
                restaurantsResponseSingle = dataManager.getRestaurantsApiCall();
                break;
            case PREPARE_MY_RESTAURANTS:
                restaurantsResponseSingle = dataManager.getSubscriptionsApiCall();
                break;
            default:
                System.out.println("PAZIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII*************");
                return;
        }

        getCompositeDisposable().add(restaurantsResponseSingle
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantsList(response.getData());
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
