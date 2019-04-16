package com.mindorks.framework.mvp.ui.manager.restaurant.details;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
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
    public void onViewPrepared(Long restaurantId) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getRestaurantDetailsApiCall(restaurantId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantDetailsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantDetailsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            // TODO vi3: ovo je samo za tesiranje
                            List<RestaurantDetailsResponse.Kitchen> kitchenList = new ArrayList<>();
                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(1L, "Kineska"));
                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(2L,
                                    "Italijanska"));
                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(3L, "Srpska"));
                            response.getData().setKitchens(kitchenList);
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

    @Override
    public void submitRestaurantDetails(RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .postRestaurantDetailsApiCall(restaurantDetails)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantDetailsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantDetailsResponse response)
                            throws Exception {
                        // TODO vi3: odraditi i prikaz gresaka, ako je update neuspesan
                        if (response != null && response.getData() != null) {
                            // TODO vi3: ovo je samo za tesiranje
                            List<RestaurantDetailsResponse.Kitchen> kitchenList = new ArrayList<>();
                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(1L, "Kineska"));
                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(2L,
                                    "Italijanska"));
                            kitchenList.add(new RestaurantDetailsResponse.Kitchen(3L, "Srpska"));
                            response.getData().setKitchens(kitchenList);
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

    @Override
    public void getAndPrepareKitchensForAutocomplete() {
        getMvpView().showLoading();
        List<RestaurantDetailsResponse.Kitchen> kitchenList = new ArrayList<>();
        kitchenList.add(new RestaurantDetailsResponse.Kitchen(1L, "Kineska"));
        kitchenList.add(new RestaurantDetailsResponse.Kitchen(2L,
                "Italijanska"));
        kitchenList.add(new RestaurantDetailsResponse.Kitchen(3L, "Srpska"));
        kitchenList.add(new RestaurantDetailsResponse.Kitchen(4L, "Svapska"));
        kitchenList.add(new RestaurantDetailsResponse.Kitchen(5L, "Grcka"));
        // TODO vi3: ovde ide API da se dobave kuhinje

        getMvpView().prepareKitchensForAutocomplete(kitchenList);

        getMvpView().hideLoading();
    }
}
