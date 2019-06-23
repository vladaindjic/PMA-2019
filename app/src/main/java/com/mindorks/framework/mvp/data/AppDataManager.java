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

package com.mindorks.framework.mvp.data;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.mindorks.framework.mvp.data.db.DbHelper;
import com.mindorks.framework.mvp.data.db.model.KitchenOption;
import com.mindorks.framework.mvp.data.db.model.Notification;
import com.mindorks.framework.mvp.data.db.model.Option;
import com.mindorks.framework.mvp.data.db.model.Question;
import com.mindorks.framework.mvp.data.db.model.User;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.ApiHeader;
import com.mindorks.framework.mvp.data.network.ApiHelper;
import com.mindorks.framework.mvp.data.network.model.AllKitchensResponse;
import com.mindorks.framework.mvp.data.network.model.BlogResponse;
import com.mindorks.framework.mvp.data.network.model.ComentVoteRequest;
import com.mindorks.framework.mvp.data.network.model.CommentRequest;
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.DishRequestDto;
import com.mindorks.framework.mvp.data.network.model.FilterRestaurantRequest;
import com.mindorks.framework.mvp.data.network.model.LoginRequest;
import com.mindorks.framework.mvp.data.network.model.LoginResponse;
import com.mindorks.framework.mvp.data.network.model.LogoutResponse;
import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.data.network.model.OpenSourceResponse;
import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantScoreRequest;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationRequest;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationResponse;
import com.mindorks.framework.mvp.data.network.model.manager.RestaurantDishesResponse;
import com.mindorks.framework.mvp.data.prefs.PreferencesHelper;
import com.mindorks.framework.mvp.di.ApplicationContext;
import com.mindorks.framework.mvp.utils.AppConstants;
import com.mindorks.framework.mvp.utils.CommonUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by janisharali on 27/01/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Observable<Long> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest
                                                              request) {
        return mApiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest
                                                                request) {
        return mApiHelper.doFacebookLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
                                                              request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Single<UserRegistrationResponse> doUserRegistrationApiCall(UserRegistrationRequest request) {
        return mApiHelper.doUserRegistrationApiCall(request);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserRole() {
        return mPreferencesHelper.getCurrentUserRole();
    }

    @Override
    public void setCurrentUserRole(String userRole) {
        mPreferencesHelper.setCurrentUserRole(userRole);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public boolean isDarkThemeOn() {
        return mPreferencesHelper.isDarkThemeOn();
    }

    @Override
    public boolean isNotificationsTurnedOn() {
        return mPreferencesHelper.isNotificationsTurnedOn();
    }

    @Override
    public boolean isSaveNetworkDataOn() {
        return mPreferencesHelper.isSaveNetworkDataOn();
    }

    @Override
    public String getActiveLanguage() {
        return mPreferencesHelper.getActiveLanguage();
    }

    @Override
    public void setActiveUserFilterId(Long userFilterId) {
        mPreferencesHelper.setActiveUserFilterId(userFilterId);
    }

    @Override
    public Long getActiveUserFilterId() {
        return mPreferencesHelper.getActiveUserFilterId();
    }

    @Override
    public Long getRestaurantIdManager() {
        return mPreferencesHelper.getRestaurantIdManager();
    }

    @Override
    public void setRestaurantIdManager(Long restaurantId) {
        mPreferencesHelper.setRestaurantIdManager(restaurantId);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath,
            String userRole) {

        // TODO vi3: sacuvati sve sta treba u skladu sa nasim korisnicima

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);
        setCurrentUserRole(userRole);

        updateApiHeader(userId, accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null,
                null);
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mDbHelper.isQuestionEmpty();
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override
    public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override
    public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override
    public Observable<Boolean> saveQuestionList(List<Question> questionList) {
        return mDbHelper.saveQuestionList(questionList);
    }

    @Override
    public Observable<Boolean> saveOptionList(List<Option> optionList) {
        return mDbHelper.saveOptionList(optionList);
    }

    @Override
    public Observable<List<Notification>> getAllNotification() {
        return mDbHelper.getAllNotification();
    }

    @Override
    public Observable<Boolean> saveNotification(Notification notification) {
        return mDbHelper.saveNotification(notification);
    }

    @Override
    public Observable<Long> saveUserFilter(UserFilter userFilter) {
        return mDbHelper.saveUserFilter(userFilter);
    }

    @Override
    public Observable<UserFilter> getUserFilter(long id) {
        return mDbHelper.getUserFilter(id);
    }

    @Override
    public Observable<Boolean> saveKitchenOption(KitchenOption kitchenOption) {
        return mDbHelper.saveKitchenOption(kitchenOption);
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isQuestionEmpty()
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
                            throws Exception {
                        if (isEmpty) {
                            Type type = $Gson$Types
                                    .newParameterizedTypeWithOwner(null, List.class,
                                            Question.class);
                            List<Question> questionList = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DATABASE_QUESTIONS),
                                    type);

                            return saveQuestionList(questionList);
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Observable<Boolean> seedDatabaseOptions() {

        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        return mDbHelper.isOptionEmpty()
                .concatMap(new Function<Boolean, ObservableSource<? extends Boolean>>() {
                    @Override
                    public ObservableSource<? extends Boolean> apply(Boolean isEmpty)
                            throws Exception {
                        if (isEmpty) {
                            Type type = new TypeToken<List<Option>>() {
                            }
                                    .getType();
                            List<Option> optionList = gson.fromJson(
                                    CommonUtils.loadJSONFromAsset(mContext,
                                            AppConstants.SEED_DATABASE_OPTIONS),
                                    type);

                            return saveOptionList(optionList);
                        }
                        return Observable.just(false);
                    }
                });
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
    }

    @Override
    public Single<RestaurantsResponse> getRestaurantsApiCall(FilterRestaurantRequest filterRestaurantRequest) {
        return mApiHelper.getRestaurantsApiCall(filterRestaurantRequest);
    }

    @Override
    public Single<RestaurantsResponse> getSubscriptionsApiCall() {
        return mApiHelper.getSubscriptionsApiCall();
    }

    @Override
    public Single<RestaurantPromotionsResponse> getRestaurantPromotions(Long restaurantId) {
        return  mApiHelper.getRestaurantPromotions(restaurantId);
    }

    @Override
    public Single<PromotionDetailsResponse> getPromotionDetails(Long promotionId) {
        return  mApiHelper.getPromotionDetails(promotionId);
    }


    public Single<RestaurantDetailsResponse> getRestaurantDetailsApiCall(Long restaurantId) {
        return mApiHelper.getRestaurantDetailsApiCall(restaurantId);
    }

    @Override
    public Single<RestaurantDetailsResponse> putRestaurantSubscribeApiCall(Long restaurantId) {
        return mApiHelper.putRestaurantSubscribeApiCall(restaurantId);
    }

    @Override
    public Single<MenuResponse> getRestaurantMenuApiCall(Long restaurantId) {
        return mApiHelper.getRestaurantMenuApiCall(restaurantId);
    }

    @Override
    public Single<DailyMenuResponse> getRestaurantDailyMenuApiCall(Long restaurantId) {
        return mApiHelper.getRestaurantDailyMenuApiCall(restaurantId);
    }

    @Override
    public Single<RestaurantRatingResponse> getRestaurantRatingApiCall(Long restaurantId) {
        return mApiHelper.getRestaurantRatingApiCall(restaurantId);
    }


    public Single<RestaurantFilterResponse> getRestaurantFilterApiCall() {
        return  mApiHelper.getRestaurantFilterApiCall();
    }

    @Override
    public Single<NotificationResponse> getNotificationsApiCall() {
        return mApiHelper.getNotificationsApiCall();
    }

    @Override
    public Single<SettingsResponse> getSettingsApiCall() {
        return mApiHelper.getSettingsApiCall();
    }

    @Override
    public Single<UserDetailsResponse> getUserDetailsApiCall(Long userId) {
        return mApiHelper.getUserDetailsApiCall(userId);
    }

    @Override
    public Single<RestaurantRatingResponse> getDishRatingApiCall(Long id) {
        return mApiHelper.getDishRatingApiCall(id);
    }
    public Single<RestaurantDetailsResponse> putRestaurantDetailsApiCall(RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        return mApiHelper.putRestaurantDetailsApiCall(restaurantDetails);
    }

    @Override
    public Single<MenuResponse> putMenuApiCall(MenuResponse.Menu menu) {
        return mApiHelper.putMenuApiCall(menu);
    }

    @Override
    public Single<RestaurantCookResponse> getRestaurantCookApiCall(Long restaurantId) {
        return mApiHelper.getRestaurantCookApiCall(restaurantId);
    }

    @Override
    public Single<RestaurantDishesResponse> getRestaurantDishesApiCall(Long restaurantId) {
        return mApiHelper.getRestaurantDishesApiCall(restaurantId);
    }

    @Override
    public Single<MealResponse> getMealApiCall(Long mealId) {
        return mApiHelper.getMealApiCall(mealId);
    }

    @Override
    public Single<DishDetailsResponse> getDishDetailsApiCall(Long dishId) {
        return mApiHelper.getDishDetailsApiCall(dishId);
    }

//    @Override
//    public Single<UserDetailsResponse> putUserImageUpdate(byte[] imageBytes) {
//        return mApiHelper.putUserImageUpdate(imageBytes);
//    }


    @Override
    public Single<UserDetailsResponse> putUserImageUpdate(File imageBytes) {
        return mApiHelper.putUserImageUpdate(imageBytes);
    }

    @Override
    public Single<UserDetailsResponse> putUserImageUpdateRaw(byte[] imageBytes) {
        return mApiHelper.putUserImageUpdateRaw(imageBytes);
    }

    @Override
    public Single<RestaurantDetailsResponse> putRestaurantImageUpdateRaw(byte[] imageBytes) {
        return  mApiHelper.putRestaurantImageUpdateRaw(imageBytes);
    }

    @Override
    public Single<RestaurantPromotionsResponse> putPromotionImageUpdateRaw(byte[] imageBytes, Long promotionId) {
        return mApiHelper.putPromotionImageUpdateRaw(imageBytes,promotionId);
    }

    @Override
    public Single<Double> rateRestaurant(Long restaurantid, RestaurantScoreRequest restaurantScoreRequest) {
        return mApiHelper.rateRestaurant(restaurantid,restaurantScoreRequest);
    }

    @Override
    public Single<Double> rateDish(Long dishId, RestaurantScoreRequest scoreRequest) {
        return mApiHelper.rateDish(dishId,scoreRequest);
    }

    @Override
    public Single<RestaurantRatingResponse> postComment(Long restaurantId, CommentRequest request) {
        return mApiHelper.postComment(restaurantId,request);
    }

    @Override
    public Single<RestaurantRatingResponse> leaevComment(Long dishId, CommentRequest request) {
        return mApiHelper.leaevComment(dishId,request);
    }

    @Override
    public Single<RestaurantRatingResponse> voteComment(Long id, ComentVoteRequest request) {
        return mApiHelper.voteComment(id,request);
    }

    @Override
    public Single<RestaurantRatingResponse> voteCommentDish(Long id, ComentVoteRequest request) {
        return  mApiHelper.voteCommentDish(id,request);
    }

    @Override
    public Single<AllKitchensResponse> getAllKitchensApiCall() {
        return  mApiHelper.getAllKitchensApiCall();
    }

    @Override
    public Single<AllKitchensResponse> getAllKitchensForRestaurant(Long restaurantId) {
        return mApiHelper.getAllKitchensForRestaurant(restaurantId);
    }

    @Override
    public Single<RestaurantCookResponse> deleteDish(Long id) {
        return  mApiHelper.deleteDish(id);
    }

    @Override
    public Single<RestaurantPromotionsResponse> deletePromotion(Long promotionId) {
        return mApiHelper.deletePromotion(promotionId);
    }

    @Override
    public Single<DailyMenuResponse> deleteMeal(Long mealId) {
        return mApiHelper.deleteMeal(mealId);
    }

    @Override
    public Single<PromotionDetailsResponse> createPromotion(PromotionDetailsResponse.Promotion promotion) {
        return  mApiHelper.createPromotion(promotion);
    }

    @Override
    public Single<RestaurantPromotionsResponse> updatePromotion(Long promotionId, PromotionDetailsResponse.Promotion promotion) {
        return mApiHelper.updatePromotion(promotionId,promotion);
    }

    @Override
    public Single<RestaurantCookResponse> addDish(Long restaurantId, DishRequestDto requestData) {
        return mApiHelper.addDish(restaurantId,requestData);
    }

    @Override
    public Single<RestaurantCookResponse> updateDish(Long dishId, DishRequestDto requestData) {
        return  mApiHelper.updateDish(dishId,requestData);
    }
}
