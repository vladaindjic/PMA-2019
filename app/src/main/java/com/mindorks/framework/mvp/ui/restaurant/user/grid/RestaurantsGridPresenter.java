package com.mindorks.framework.mvp.ui.restaurant.user.grid;

import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RestaurantsGridPresenter<V extends RestaurantsGridMvpView> extends BasePresenter<V>
        implements RestaurantsGridMvpPresenter<V> {

    @Inject
    public RestaurantsGridPresenter(DataManager dataManager,
                                    SchedulerProvider schedulerProvider,
                                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);


    }
}
