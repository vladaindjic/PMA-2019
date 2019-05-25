package com.mindorks.framework.mvp.ui.user.dish.ratings;

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

public class UserDishRatingPresenter<V extends UserDishRatingMvpView> extends BasePresenter<V>
        implements UserDishRatingMvpPresenter<V> {

    private static final String TAG = "UserDishRatingPresenter";

    @Inject
    public UserDishRatingPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(Long dishId) {
        getCompositeDisposable().add(getDataManager()
                .getDishRatingApiCall(dishId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantRatingResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantRatingResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateDishRating(response.getData());
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
    public void rateDish(Long id, final RatingBar ratingBar) {
        RestaurantScoreRequest request = new RestaurantScoreRequest((int)ratingBar.getRating());

        getCompositeDisposable().add(getDataManager()
                .rateDish(id,request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Double>() {

                    @Override
                    public void accept(@NonNull Double response)
                            throws Exception {
                        if (response != null) {
                            getMvpView().updateDishRatingValue(response);
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
    public void leaveComment(Long id, String text) {

        CommentRequest request = new CommentRequest(text);

        getCompositeDisposable().add(getDataManager()
                .leaevComment(id,request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantRatingResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantRatingResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateDishRating(response.getData());
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
    public void voteComent(Long id, ComentVoteRequest request) {

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .voteCommentDish(id,request)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RestaurantRatingResponse>() {
                    @Override
                    public void accept(@NonNull RestaurantRatingResponse response)
                            throws Exception {
                        if (response != null && response.getData() != null) {
                            getMvpView().updateDishRating(response.getData());
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


    // TODO SREDITI vi3: pozovi API
}
