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
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.data.network.model.OpenSourceResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
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
import com.mindorks.framework.mvp.ui.filter.RestaurantFilterKitchenOptionsAdapter;
import com.mindorks.framework.mvp.ui.filter.RestaurantFilterMvpPresenter;
import com.mindorks.framework.mvp.ui.filter.RestaurantFilterMvpView;
import com.mindorks.framework.mvp.ui.filter.RestaurantFilterOptionsAdapter;
import com.mindorks.framework.mvp.ui.filter.RestaurantFilterPresenter;
import com.mindorks.framework.mvp.ui.login.LoginMvpPresenter;
import com.mindorks.framework.mvp.ui.login.LoginMvpView;
import com.mindorks.framework.mvp.ui.login.LoginPresenter;
import com.mindorks.framework.mvp.ui.main.MainMvpPresenter;
import com.mindorks.framework.mvp.ui.main.MainMvpView;
import com.mindorks.framework.mvp.ui.main.MainPresenter;
import com.mindorks.framework.mvp.ui.main.rating.RatingDialogMvpPresenter;
import com.mindorks.framework.mvp.ui.main.rating.RatingDialogMvpView;
import com.mindorks.framework.mvp.ui.main.rating.RatingDialogPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantMvpView;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantPagerAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.cook.ManagerRestaurantCookItemListAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.cook.ManagerRestaurantCookMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.cook.ManagerRestaurantCookMvpView;
import com.mindorks.framework.mvp.ui.manager.restaurant.cook.ManagerRestaurantCookPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.ManagerMealListAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.ManagerRestaurantDailyMenuMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.ManagerRestaurantDailyMenuMvpView;
import com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.ManagerRestaurantDailyMenuPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.details.ManagerRestaurantDetailsKitchensAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.details.ManagerRestaurantDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.details.ManagerRestaurantDetailsMvpView;
import com.mindorks.framework.mvp.ui.manager.restaurant.details.ManagerRestaurantDetailsPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.dish.ManagerDishDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.dish.ManagerDishDetailsMvpView;
import com.mindorks.framework.mvp.ui.manager.restaurant.dish.ManagerDishDetailsPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerDishListAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerDishTypeListAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerRestaurantMenuMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerRestaurantMenuMvpView;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerRestaurantMenuPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsMvpView;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.ManagerRestaurantPromotionsPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details.ManagerRestaurantPromotionDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details.ManagerRestaurantPromotionDetailsMvpView;
import com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details.ManagerRestaurantPromotionDetailsPresenter;
import com.mindorks.framework.mvp.ui.notification.NotificationListAdapter;
import com.mindorks.framework.mvp.ui.notification.NotificationMvpPresenter;
import com.mindorks.framework.mvp.ui.notification.NotificationMvpView;
import com.mindorks.framework.mvp.ui.notification.NotificationPresenter;
import com.mindorks.framework.mvp.ui.settings.GeneralSettingsOptionsAdapter;
import com.mindorks.framework.mvp.ui.settings.SettingsMvpPresenter;
import com.mindorks.framework.mvp.ui.settings.SettingsMvpView;
import com.mindorks.framework.mvp.ui.settings.SettingsPresenter;
import com.mindorks.framework.mvp.ui.splash.SplashMvpPresenter;
import com.mindorks.framework.mvp.ui.splash.SplashMvpView;
import com.mindorks.framework.mvp.ui.splash.SplashPresenter;
import com.mindorks.framework.mvp.ui.user.details.UserDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.details.UserDetailsMvpView;
import com.mindorks.framework.mvp.ui.user.details.UserDetailsPresenter;
import com.mindorks.framework.mvp.ui.user.dish.UserDishMvpPresenter;
import com.mindorks.framework.mvp.ui.user.dish.UserDishMvpView;
import com.mindorks.framework.mvp.ui.user.dish.UserDishPagerAdapter;
import com.mindorks.framework.mvp.ui.user.dish.UserDishPresenter;
import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsMvpView;
import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsNutritiveValuesAdapter;
import com.mindorks.framework.mvp.ui.user.dish.details.UserDishDetailsPresenter;
import com.mindorks.framework.mvp.ui.user.dish.ratings.UserDishRatingMvpPresenter;
import com.mindorks.framework.mvp.ui.user.dish.ratings.UserDishRatingMvpView;
import com.mindorks.framework.mvp.ui.user.dish.ratings.UserDishRatingPresenter;
import com.mindorks.framework.mvp.ui.user.meal.UserMealMvpPresenter;
import com.mindorks.framework.mvp.ui.user.meal.UserMealMvpView;
import com.mindorks.framework.mvp.ui.user.meal.UserMealPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantPagerAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.MealListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsKitchensAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.DishTypeListAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.details.PromotionDetailsMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.details.PromotionDetailsMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.details.PromotionDetailsPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.ratings.UserRestaurantCommentAdapter;
import com.mindorks.framework.mvp.ui.user.restaurant.ratings.UserRestaurantRatingMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurant.ratings.UserRestaurantRatingMvpView;
import com.mindorks.framework.mvp.ui.user.restaurant.ratings.UserRestaurantRatingPresenter;
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
import com.mindorks.framework.mvp.ui.user.restaurants.map.RestaurantsMapMvpPresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.map.RestaurantsMapMvpView;
import com.mindorks.framework.mvp.ui.user.restaurants.map.RestaurantsMapPresenter;
import com.mindorks.framework.mvp.ui.user.subscrptions.SubscriptionMvpPresenter;
import com.mindorks.framework.mvp.ui.user.subscrptions.SubscriptionMvpView;
import com.mindorks.framework.mvp.ui.user.subscrptions.SubscriptionPresenter;
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


    // =================================== UserDishActivity

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

    // =================================== UserDishDetailsFragment
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
    UserRestaurantPromotionsAdapter provideRestaurantPromotionsAdapter() {
        return new UserRestaurantPromotionsAdapter(new ArrayList<RestaurantPromotionsResponse.Promotion>());
    }

    // =================================== UserDishDetailsKitchensAdapter
    @Provides
    UserRestaurantDetailsKitchensAdapter provideUserRestaurantDetailsKitchensAdapter() {
        return new UserRestaurantDetailsKitchensAdapter(new ArrayList<RestaurantDetailsResponse.Kitchen>());
    }

    @Provides
    @PerActivity
    PromotionDetailsMvpPresenter<PromotionDetailsMvpView> providePromotionDetailsMvpPresenter(
            PromotionDetailsPresenter<PromotionDetailsMvpView> presenter) {
        return presenter;
    }


    //Milan Dodao - to, Mica, majstore ;)
    //Rating Presenter
    @Provides
    UserRestaurantRatingMvpPresenter<UserRestaurantRatingMvpView> UserRestaurantRatingPresenter(
            UserRestaurantRatingPresenter<UserRestaurantRatingMvpView> presenter) {
        return presenter;
    }

    @Provides
    UserRestaurantMenuMvpPresenter<UserRestaurantMenuMvpView> UserRestaurantMenuPresenter(
            UserRestaurantMenuPresenter<UserRestaurantMenuMvpView> presenter) {
        return presenter;
    }

    @Provides
    UserRestaurantDailyMenuMvpPresenter<UserRestaurantDailyMenuMvpView> UserRestaurantDailyMenuPresenter(
            UserRestaurantDailyMenuPresenter<UserRestaurantDailyMenuMvpView> presenter) {
        return presenter;
    }

    @Provides
    UserRestaurantCommentAdapter provideUserRestaurantCommentAdapter() {
        return new UserRestaurantCommentAdapter(new ArrayList<RestaurantRatingResponse.RestaurantRating.Comment>());
    }


    // =================================== DishTypeListAdapter
    @Provides
    DishTypeListAdapter provideDishTypeListAdapter(AppCompatActivity activity) {
        return new DishTypeListAdapter(new ArrayList<MenuResponse.DishType>(), activity);
    }

    // =================================== DishListAdapter
    @Provides
    DishListAdapter provideDishListAdapter() {
        return new DishListAdapter(new ArrayList<MenuResponse.Dish>());
    }

    // =================================== UserDishMvpPresenter
    @Provides
    @PerActivity
    UserDishMvpPresenter<UserDishMvpView> provideUserDishPresenter(
            UserDishPresenter<UserDishMvpView> presenter) {
        return presenter;
    }

    // =================================== UserDishPagerAdapter
    @Provides
    UserDishPagerAdapter provideUserDishPagerAdapter(AppCompatActivity activity) {
        return new UserDishPagerAdapter(activity.getSupportFragmentManager());
    }

    // =================================== UserDishRatingMvpPresenter
    @Provides
    UserDishRatingMvpPresenter<UserDishRatingMvpView> UserDishRatingPresenter(
            UserDishRatingPresenter<UserDishRatingMvpView> presenter) {
        return presenter;
    }

    // =================================== UserDishDetailsMvpPresenter
    @Provides
    UserDishDetailsMvpPresenter<UserDishDetailsMvpView> UserDishDetailsPresenter(
            UserDishDetailsPresenter<UserDishDetailsMvpView> presenter) {
        return presenter;
    }

    // =================================== UserDishDetailsNutritiveValuesAdapter
    @Provides
    UserDishDetailsNutritiveValuesAdapter provideUserDishDetailsNutritiveValuesAdapter() {
        return new UserDishDetailsNutritiveValuesAdapter(new ArrayList<DishDetailsResponse.NutritiveValue>());
    }

    // =================================== MealListAdapter
    @Provides
    MealListAdapter provideMealListAdapter() {
        return new MealListAdapter(new ArrayList<DailyMenuResponse.Meal>());
    }

    @Provides
    RestaurantFilterMvpPresenter<RestaurantFilterMvpView> RestaurantFilterPresenter(
            RestaurantFilterPresenter<RestaurantFilterMvpView> presenter) {
        return presenter;
    }

    @Provides
    RestaurantFilterKitchenOptionsAdapter provideRestaurantFilterKitchenOptionAdapter() {
        return new RestaurantFilterKitchenOptionsAdapter(new ArrayList<RestaurantFilterResponse.RestaurantFilter.KitchenOptions>());
    }

    @Provides
    RestaurantFilterOptionsAdapter provideRestaurantFilterOptionsAdapter() {
        return new RestaurantFilterOptionsAdapter(new ArrayList<RestaurantFilterResponse.RestaurantFilter.RestaurantFilterOptions>());
    }

    // =================================== UserMealMvpPresenter
    @Provides
    @PerActivity
    UserMealMvpPresenter<UserMealMvpView> provideUserMealPresenter(
            UserMealPresenter<UserMealMvpView> presenter) {
        return presenter;
    }

    // =================================== RestaurantsMapMvpPresenter<RestaurantsMapMvpView>
    @Provides
    RestaurantsMapMvpPresenter<RestaurantsMapMvpView> provideRestaurantsMapPresenter(
            RestaurantsMapPresenter<RestaurantsMapMvpView> presenter) {
        return presenter;
    }

    @Provides
    NotificationListAdapter provideNotificationAdapter() {
        return new NotificationListAdapter(new ArrayList<NotificationResponse.Notifications.Notification>());
    }

    @Provides
    NotificationMvpPresenter<NotificationMvpView> NotificationPresenter(
            NotificationPresenter<NotificationMvpView> presenter) {
        return presenter;
    }

    // ========================================= SubscriptionMvpPresenter<SubscriptionMvpView>
    @Provides
    @PerActivity
    SubscriptionMvpPresenter<SubscriptionMvpView> provideSubscriptionPresenter(
            SubscriptionPresenter<SubscriptionMvpView> presenter) {
        return presenter;
    }

    @Provides
    SettingsMvpPresenter<SettingsMvpView> SettingsPresenter(
            SettingsPresenter<SettingsMvpView> presenter) {
        return presenter;
    }

    @Provides
    GeneralSettingsOptionsAdapter provideGeneralSettingsOptionsAdapter() {
        return new GeneralSettingsOptionsAdapter(new ArrayList<SettingsResponse.SettingsData.SettingsGeneralOption>());
    }

    @Provides
    UserDetailsMvpPresenter<UserDetailsMvpView> provideUserDetailsPresenter(
            UserDetailsPresenter<UserDetailsMvpView> presenter) {
        return presenter;
    }


//    @Provides
//    LanguageSettingsOptionsAdapter provideLanguageSettingsOptionsAdapter(Context context) {
//        return new LanguageSettingsOptionsAdapter(context, new ArrayList<SettingsResponse.SettingsData.LanguageOption>());
//    }


    // ==================================== Manager ===========================
    @Provides
    @PerActivity
    ManagerRestaurantMvpPresenter<ManagerRestaurantMvpView> provideManagerRestaurantPresenter(
            ManagerRestaurantPresenter<ManagerRestaurantMvpView> presenter) {
        return presenter;
    }

    @Provides
    ManagerRestaurantPagerAdapter provideManagerRestaurantPagerAdapter(AppCompatActivity activity) {
        return new ManagerRestaurantPagerAdapter(activity.getSupportFragmentManager());
    }

    // =========== ManagerRestaurantDetailsFragment
    @Provides
    ManagerRestaurantDetailsMvpPresenter<ManagerRestaurantDetailsMvpView> ManagerRestaurantDetailsPresenter(
            ManagerRestaurantDetailsPresenter<ManagerRestaurantDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    ManagerRestaurantDetailsKitchensAdapter provideManagerRestaurantDetailsKitchensAdapter() {
        return new ManagerRestaurantDetailsKitchensAdapter(new ArrayList<RestaurantDetailsResponse.Kitchen>());
    }


    // ============ ManagerRestaurantCookFragment
    @Provides
    ManagerRestaurantCookMvpPresenter<ManagerRestaurantCookMvpView> ManagerRestaurantCookPresenter(
            ManagerRestaurantCookPresenter<ManagerRestaurantCookMvpView> presenter) {
        return presenter;
    }

    // ============= ManagerRestaurantMenuFragment
    @Provides
    ManagerRestaurantMenuMvpPresenter<ManagerRestaurantMenuMvpView> ManagerRestaurantMenuPresenter(
            ManagerRestaurantMenuPresenter<ManagerRestaurantMenuMvpView> presenter) {
        return presenter;
    }

    @Provides
    ManagerDishTypeListAdapter provideManagerDishTypeListAdapter(AppCompatActivity activity) {
        return new ManagerDishTypeListAdapter(new ArrayList<MenuResponse.DishType>(), activity);
    }

    @Provides
    ManagerDishListAdapter provideManagerDishListAdapter() {
        return new ManagerDishListAdapter(new ArrayList<MenuResponse.Dish>());
    }


    // ============ ManagerRestaurantDailyMenuFragment
    @Provides
    ManagerRestaurantDailyMenuMvpPresenter<ManagerRestaurantDailyMenuMvpView> ManagerRestaurantDailyMenuPresenter(
            ManagerRestaurantDailyMenuPresenter<ManagerRestaurantDailyMenuMvpView> presenter) {
        return presenter;
    }

    @Provides
    ManagerMealListAdapter provideManagerMealListAdapter() {
        return new ManagerMealListAdapter(new ArrayList<DailyMenuResponse.Meal>());
    }


    // ============ ManagerRestaurantPromotionsFragment
    @Provides
    ManagerRestaurantPromotionsMvpPresenter<ManagerRestaurantPromotionsMvpView> ManagerRestaurantPromotionsPresenter(
            ManagerRestaurantPromotionsPresenter<ManagerRestaurantPromotionsMvpView> presenter) {
        return presenter;
    }

    @Provides
    ManagerRestaurantPromotionsAdapter provideManagerRestaurantPromotionsAdapter() {
        return new ManagerRestaurantPromotionsAdapter(new ArrayList<RestaurantPromotionsResponse.Promotion>());
    }

    @Provides
    ManagerRestaurantCookItemListAdapter provideManagerRestaurantCookItemListAdapter() {
        return new ManagerRestaurantCookItemListAdapter(new ArrayList<RestaurantCookResponse.RestaurantCook.RestaurantCookItem>());
    }

    // ==================================== Manager ===========================

    @Provides
    ManagerRestaurantPromotionDetailsMvpPresenter<ManagerRestaurantPromotionDetailsMvpView> ManagerRestaurantPromotionDetailsPresenter(
            ManagerRestaurantPromotionDetailsPresenter<ManagerRestaurantPromotionDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    ManagerDishDetailsMvpPresenter<ManagerDishDetailsMvpView> ManagerDishDetailsPresenter(
            ManagerDishDetailsPresenter<ManagerDishDetailsMvpView> presenter) {
        return presenter;
    }
}
