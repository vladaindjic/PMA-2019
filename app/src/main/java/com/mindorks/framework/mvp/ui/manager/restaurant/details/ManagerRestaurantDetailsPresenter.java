package com.mindorks.framework.mvp.ui.manager.restaurant.details;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.KitchenOption;
import com.mindorks.framework.mvp.data.network.model.AllKitchensResponse;
import com.mindorks.framework.mvp.data.network.model.FilterRestaurantRequest;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ManagerRestaurantDetailsPresenter<V extends ManagerRestaurantDetailsMvpView> extends BasePresenter<V>
        implements ManagerRestaurantDetailsMvpPresenter<V> {

    private static final String TAG = "UserDishDetailsPresenter";

    @Inject
    public ManagerRestaurantDetailsPresenter(DataManager dataManager,
                                             SchedulerProvider schedulerProvider,
                                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getRestaurantDetailsApiCall(getDataManager().getRestaurantIdManager())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantDetailsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantDetailsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantDetails(response.getData());
                            getAndPrepareKitchensForAutocomplete();
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
    public void submitRestaurantDetails(RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .putRestaurantDetailsApiCall(restaurantDetails)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantDetailsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantDetailsResponse response)
                            throws Exception {
                        // TODO vi3: odraditi i prikaz gresaka, ako je update neuspesan
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantDetails(response.getData());
                            // ovde da puknemo sliku
                            submitRestaurantImage();
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

    public void submitRestaurantImage() {
        byte[] imgBytes = getMvpView().getImgBytes();
        System.out.println("VI3: UPDATE SLIKE RESTORANA: " + imgBytes != null ? imgBytes.length :
                0);
        if (imgBytes != null) {
            getCompositeDisposable().add(getDataManager()
                    .putRestaurantImageUpdateRaw(imgBytes)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<RestaurantDetailsResponse>() {
                        @Override
                        public void accept(@NonNull RestaurantDetailsResponse response)
                                throws Exception {
                            if (response != null && response.getData() != null) {
                                getMvpView().updateRestaurantDetails(response.getData());
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

    @Override
    public void getAndPrepareKitchensForAutocomplete() {
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
                            // posto imamo razlicite objekte
                            List<RestaurantDetailsResponse.Kitchen> kitchenList = new ArrayList<>();
                            RestaurantDetailsResponse.Kitchen k;
                            for(RestaurantFilterResponse.RestaurantFilter.KitchenOptions ko:
                                    response.getData()){
                                k = new RestaurantDetailsResponse.Kitchen();
                                k.setId(ko.getId());
                                k.setName(ko.getName());
                                kitchenList.add(k);
                            }
                            getMvpView().prepareKitchensForAutocomplete(kitchenList);
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

        getMvpView().hideLoading();
    }
}
