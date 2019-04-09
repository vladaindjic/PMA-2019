package com.mindorks.framework.mvp.ui.user.restaurant.promotions;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserRestaurantPromotionsPresenter<V extends UserRestaurantPromotionsMvpView> extends BasePresenter<V>
        implements UserRestaurantPromotionsMvpPresenter<V> {

    private static final String TAG = "UserRestaurantPromotionsPresenter";

    @Inject
    public UserRestaurantPromotionsPresenter(DataManager dataManager,
                                             SchedulerProvider schedulerProvider,
                                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {

        getMvpView().showLoading();

//        List<RestaurantPromotionsResponse.Promotion> promotionList = new ArrayList<>();
//
//        RestaurantPromotionsResponse.Promotion p1 = new RestaurantPromotionsResponse.Promotion(1,"Promocija 1"
//                , "https://runningonrealfood.com/wp-content/uploads/2013/05/chickpea-veggie-burgers-20-running-on-real-food.jpg");
//        promotionList.add(p1);
//
//        getMvpView().updateRestaurantPromotionsList(promotionList);



        getCompositeDisposable().add(getDataManager()
                .getRestaurantPromotions()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantPromotionsResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantPromotionsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantPromotionsList(response.getData());
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
