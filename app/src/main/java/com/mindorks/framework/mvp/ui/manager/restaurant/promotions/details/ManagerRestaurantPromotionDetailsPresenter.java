package com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsMvpPresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ManagerRestaurantPromotionDetailsPresenter<V extends ManagerRestaurantPromotionDetailsMvpView>
        extends BasePresenter<V> implements ManagerRestaurantPromotionDetailsMvpPresenter<V> {

    private static final String TAG = "ManagerRestaurantPromotionDetailsPresenter";

    @Inject
    public ManagerRestaurantPromotionDetailsPresenter(DataManager dataManager,
                                                      SchedulerProvider schedulerProvider,
                                                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void loadData(Long promotionId) {

        //TODO Milan: Iskoristiti id promocije
        getMvpView().showLoading();


        getCompositeDisposable().add(getDataManager()
                .getPromotionDetails(promotionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<PromotionDetailsResponse>() {
                    @Override
                    public void accept(@NonNull PromotionDetailsResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updatePromotion(response.getData());
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

    @Override
    public void savePromotion(PromotionDetailsResponse.Promotion promotion) {

        //TODO Milan: Ovdije obaviti poziv ka beku
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .createPromotion(promotion)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<PromotionDetailsResponse>() {
                    @Override
                    public void accept(PromotionDetailsResponse response) throws Exception {
                        //TODO:SLIKA
                        if(response!=null && response.getData()!=null) {
                            getMvpView().updatePromotion(response.getData());
                            submitPromotionImage(response.getData().getId());
                        }
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("GRESKAA!");
                    }
                }));

        getMvpView().hideLoading();
    }

    public void submitPromotionImage(Long promotionId) {
        byte[] imgBytes = getMvpView().getImgBytes();
        if (imgBytes != null) {
            getCompositeDisposable().add(getDataManager()
                    .putPromotionImageUpdateRaw(imgBytes,promotionId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<RestaurantPromotionsResponse>() {
                        @Override
                        public void accept(@NonNull RestaurantPromotionsResponse response)
                                throws Exception {
                            if (response != null && response.getData() != null) {
                                getMvpView().back();
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
        getMvpView().back();

    }

    @Override
    public void updatePromotion(final long promotionId, PromotionDetailsResponse.Promotion editedPromotion) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .updatePromotion(promotionId, editedPromotion)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantPromotionsResponse>() {
                    @Override
                    public void accept(RestaurantPromotionsResponse restaurantPromotionsResponse) throws Exception {
                        submitPromotionImage(promotionId);
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("GRESKAA IZMENA!");
                    }
                }));

        getMvpView().hideLoading();
    }

}
