package com.mindorks.framework.mvp.ui.manager.restaurant.cook;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ManagerRestaurantCookPresenter<V extends ManagerRestaurantCookMvpView> extends BasePresenter<V>
        implements ManagerRestaurantCookMvpPresenter<V> {

    private static final String TAG = "ManagerRestaurantCookPresenter";

    @Inject
    public ManagerRestaurantCookPresenter(DataManager dataManager,
                                          SchedulerProvider schedulerProvider,
                                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long restaurantId) {
        getMvpView().showLoading();
        getMvpView().hideLoading();

        // TODO vi3: kreirati API poziv koji vrati sva jela, bez kategorija
        System.out.println("ovde sam*******************************************");
        getCompositeDisposable().add(getDataManager()
                .getRestaurantCookApiCall(restaurantId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantCookResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantCookResponse response)
                            throws Exception {
                        System.out.println(response);
                        System.out.println(response.getData().getRestaurantCookItemList().size());
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantCook(response.getData().getRestaurantCookItemList());
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
