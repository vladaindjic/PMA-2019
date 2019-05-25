package com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserRestaurantDailyMenuPresenter<V extends UserRestaurantDailyMenuMvpView> extends BasePresenter<V>
        implements UserRestaurantDailyMenuMvpPresenter<V> {


    private static final String TAG = "UserRestaurantDailyMenuPresenter";

    @Inject
    public UserRestaurantDailyMenuPresenter(DataManager dataManager,
                                            SchedulerProvider schedulerProvider,
                                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long restaurantId) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getRestaurantDailyMenuApiCall(restaurantId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<DailyMenuResponse>() {
                    @Override
                    public void accept(@NonNull DailyMenuResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateDailyMenu(response.getData());
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
