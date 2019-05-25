package com.mindorks.framework.mvp.ui.user.restaurant.ratings;

import android.widget.RatingBar;

import com.androidnetworking.error.ANError;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.network.model.ComentVoteRequest;
import com.mindorks.framework.mvp.data.network.model.CommentRequest;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantScoreRequest;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserRestaurantRatingPresenter<V extends UserRestaurantRatingMvpView> extends BasePresenter<V>
        implements UserRestaurantRatingMvpPresenter<V> {

    private static final String TAG = "UserDishRatingPresenter";

    @Inject
    public UserRestaurantRatingPresenter(DataManager dataManager,
                                         SchedulerProvider schedulerProvider,
                                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long restaurantId) {


        getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager()
                    .getRestaurantRatingApiCall(restaurantId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<RestaurantRatingResponse>() {
                        @Override
                        public void accept(@NonNull RestaurantRatingResponse response)
                                throws Exception {
                            if (response != null && response.getData() != null) {
                                getMvpView().updateRestaurantRating(response.getData());
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
    public void rateRestaurant(Long restaurantId, final RatingBar ratingBar) {

        RestaurantScoreRequest request = new RestaurantScoreRequest((int)ratingBar.getRating());

        getCompositeDisposable().add(getDataManager()
                .rateRestaurant(restaurantId,request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Double>() {

                    @Override
                    public void accept(@NonNull Double response)
                            throws Exception {
                        if (response != null) {
                            getMvpView().updateRestaurantRatingValue(response);
                            ratingBar.setIsIndicator(true);
                            getMvpView().showMessage("Uspesno ste glasali!");
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
    public void leaveComment(Long restaurantId, String text) {

        CommentRequest request = new CommentRequest(text);

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .postComment(restaurantId,request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantRatingResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantRatingResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantRating(response.getData());
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
    public void likeComment(Long id) {
        ComentVoteRequest request = new ComentVoteRequest("POSITIVE");

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .voteComment(id,request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantRatingResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantRatingResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantRating(response.getData());
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
    public void dislikeComment(Long id) {
        ComentVoteRequest request = new ComentVoteRequest("NEGATIVE");

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .voteComment(id,request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantRatingResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantRatingResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateRestaurantRating(response.getData());
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
