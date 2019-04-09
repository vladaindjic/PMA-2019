/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.mindorks.framework.mvp.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.mindorks.framework.mvp.data.network.model.BlogResponse;
import com.mindorks.framework.mvp.data.network.model.OpenSourceResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.ActivityContext;
import com.mindorks.framework.mvp.di.PerActivity;
import com.mindorks.framework.mvp.ui.about.AboutMvpPresenter;
import com.mindorks.framework.mvp.ui.about.AboutMvpView;
import com.mindorks.framework.mvp.ui.about.AboutPresenter;
import com.mindorks.framework.mvp.ui.feed.FeedMvpPresenter;
import com.mindorks.framework.mvp.ui.feed.FeedMvpView;
import com.mindorks.framework.mvp.ui.feed.FeedPagerAdapter;
import com.mindorks.framework.mvp.ui.feed.FeedPresenter;
import com.mindorks.framework.mvp.ui.feed.blogs.BlogAdapter;
import com.mindorks.framework.mvp.ui.feed.blogs.BlogMvpPresenter;
import com.mindorks.framework.mvp.ui.feed.blogs.BlogMvpView;
import com.mindorks.framework.mvp.ui.feed.blogs.BlogPresenter;
import com.mindorks.framework.mvp.ui.feed.opensource.OpenSourceAdapter;
import com.mindorks.framework.mvp.ui.feed.opensource.OpenSourceMvpPresenter;
import com.mindorks.framework.mvp.ui.feed.opensource.OpenSourceMvpView;
import com.mindorks.framework.mvp.ui.feed.opensource.OpenSourcePresenter;
import com.mindorks.framework.mvp.ui.login.LoginMvpPresenter;
import com.mindorks.framework.mvp.ui.login.LoginMvpView;
import com.mindorks.framework.mvp.ui.login.LoginPresenter;
import com.mindorks.framework.mvp.ui.main.MainMvpPresenter;
import com.mindorks.framework.mvp.ui.main.MainMvpView;
import com.mindorks.framework.mvp.ui.main.MainPresenter;
import com.mindorks.framework.mvp.ui.main.rating.RatingDialogMvpPresenter;
import com.mindorks.framework.mvp.ui.main.rating.RatingDialogMvpView;
import com.mindorks.framework.mvp.ui.main.rating.RatingDialogPresenter;
import com.mindorks.framework.mvp.ui.splash.SplashMvpPresenter;
import com.mindorks.framework.mvp.ui.splash.SplashMvpView;
import com.mindorks.framework.mvp.ui.splash.SplashPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantPagerAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsMvpView;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsPagerAdapter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.grid.RestaurantsGridAdapter;
import com.mindorks.framework.mvp.ui.user.restaurants.grid.RestaurantsGridMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.grid.RestaurantsGridMvpView;
import com.mindorks.framework.mvp.ui.user.restaurants.grid.RestaurantsGridPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListMvpView;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListPresenter;
import com.mindorks.framework.mvp.ui.userRegistration.UserRegistrationMvpPresenter;
import com.mindorks.framework.mvp.ui.userRegistration.UserRegistrationMvpView;
import com.mindorks.framework.mvp.ui.userRegistration.UserRegistrationPresenter;
import com.mindorks.framework.mvp.utils.rx.AppSchedulerProvider;
import com.mindorks.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    AboutMvpPresenter<AboutMvpView> provideAboutPresenter(
            AboutPresenter<AboutMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    UserRegistrationMvpPresenter<UserRegistrationMvpView> provideUserRegistrationPresenter(
            UserRegistrationPresenter<UserRegistrationMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    UserRestaurantsMvpPresenter<UserRestaurantsMvpView> provideUserRestaurantsPresenter(
            UserRestaurantsPresenter<UserRestaurantsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    RatingDialogMvpPresenter<RatingDialogMvpView> provideRateUsPresenter(
            RatingDialogPresenter<RatingDialogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedMvpPresenter<FeedMvpView> provideFeedPresenter(
            FeedPresenter<FeedMvpView> presenter) {
        return presenter;
    }

    @Provides
    OpenSourceMvpPresenter<OpenSourceMvpView> provideOpenSourcePresenter(
            OpenSourcePresenter<OpenSourceMvpView> presenter) {
        return presenter;
    }

    @Provides
    BlogMvpPresenter<BlogMvpView> provideBlogMvpPresenter(
            BlogPresenter<BlogMvpView> presenter) {
        return presenter;
    }

    @Provides
    FeedPagerAdapter provideFeedPagerAdapter(AppCompatActivity activity) {
        return new FeedPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    OpenSourceAdapter provideOpenSourceAdapter() {
        return new OpenSourceAdapter(new ArrayList<OpenSourceResponse.Repo>());
    }

    @Provides
    BlogAdapter provideBlogAdapter() {
        return new BlogAdapter(new ArrayList<BlogResponse.Blog>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    GridLayoutManager provideGridLayoutManager(AppCompatActivity activity) {
        return new GridLayoutManager(activity, 2);
    }

    @Provides
    UserRestaurantsPagerAdapter provideUserRestaurantsPagerAdapter(AppCompatActivity activity) {
        return new UserRestaurantsPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    RestaurantsListMvpPresenter<RestaurantsListMvpView> provideRestaurantsListPresenter(
            RestaurantsListPresenter<RestaurantsListMvpView> presenter) {
        return presenter;
    }

    @Provides
    RestaurantsGridMvpPresenter<RestaurantsGridMvpView> provideRestaurantsGridPresenter(
            RestaurantsGridPresenter<RestaurantsGridMvpView> presenter) {
        return presenter;
    }

    @Provides
    RestaurantsListAdapter provideRestaurantsListAdapter() {
        return new RestaurantsListAdapter(new ArrayList<RestaurantsResponse.Restaurant>());
    }

    @Provides
    RestaurantsGridAdapter provideRestaurantsGridAdapter() {
        return new RestaurantsGridAdapter(new ArrayList<RestaurantsResponse.Restaurant>());
    }


    // =================================== UserRestaurantActivity

    @Provides
    UserRestaurantPagerAdapter provideUserRestaurantPagerAdapter(AppCompatActivity activity) {
        return new UserRestaurantPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    @PerActivity
    UserRestaurantMvpPresenter<UserRestaurantMvpView> provideUserRestaurantPresenter(
            UserRestaurantPresenter<UserRestaurantMvpView> presenter) {
        return presenter;
    }

    // =================================== UserRestaurantDetailsFragment
    @Provides
    UserRestaurantDetailsMvpPresenter<UserRestaurantDetailsMvpView> UserRestaurantDetailsPresenter(
            UserRestaurantDetailsPresenter<UserRestaurantDetailsMvpView> presenter) {
        return presenter;
    }


    // =================================== UserRestaurantPromotionsFragment
    @Provides
    UserRestaurantPromotionsMvpPresenter<UserRestaurantPromotionsMvpView> UserRestaurantPromotionsPresenter(
            UserRestaurantPromotionsPresenter<UserRestaurantPromotionsMvpView> presenter) {
        return presenter;
    }


    //Milan dodao

    //UserRestaurantPromotionsAdapter
    @Provides
    UserRestaurantPromotionsAdapter  provideRestaurantPromotionsAdapter(){
        return new UserRestaurantPromotionsAdapter(new ArrayList<RestaurantPromotionsResponse.Promotion>());
    }

}
