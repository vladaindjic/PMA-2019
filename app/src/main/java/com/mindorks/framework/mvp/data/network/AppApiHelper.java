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

package com.mindorks.framework.mvp.data.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;

import com.mindorks.framework.mvp.data.network.model.AllKitchensResponse;
import com.mindorks.framework.mvp.data.network.model.BlogResponse;
import com.mindorks.framework.mvp.data.network.model.ChangePasswordRequest;
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
import com.mindorks.framework.mvp.data.network.model.MyRestaurantsResponse;
import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.data.network.model.OpenSourceResponse;
import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantScoreRequest;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
import com.mindorks.framework.mvp.data.network.model.UpdateUserDetailsRequest;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationRequest;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationResponse;
import com.mindorks.framework.mvp.data.network.model.manager.RestaurantDishesResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.zip.DeflaterOutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by janisharali on 28/01/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private static final int IMAGE_HEIGHT = 512;

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest
                                                              request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GOOGLE_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest
                                                                request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FACEBOOK_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
                                                              request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<UserRegistrationResponse> doUserRegistrationApiCall(UserRegistrationRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_USER_REGISTRATION)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(UserRegistrationResponse.class);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(LogoutResponse.class);
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_BLOG)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(BlogResponse.class);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(OpenSourceResponse.class);
    }

    @Override
    public Single<RestaurantsResponse> getRestaurantsApiCall(FilterRestaurantRequest filterRestaurantRequest) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RESTAURANTS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(filterRestaurantRequest)
                .build()
                .getObjectSingle(RestaurantsResponse.class);
    }

    @Override
    public Single<MyRestaurantsResponse> getSubscriptionsApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_SUBSCRIPTIONS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(MyRestaurantsResponse.class);
    }


    @Override
    public Single<RestaurantPromotionsResponse> getRestaurantPromotions(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_PROMOTIONS + restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantPromotionsResponse.class);
    }

    @Override
    public Single<PromotionDetailsResponse> getPromotionDetails(Long promotionId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PROMOTION_DETAILS + promotionId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(PromotionDetailsResponse.class);
    }


    public Single<RestaurantDetailsResponse> getRestaurantDetailsApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_DETAILS + restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantDetailsResponse.class);
    }

    @Override
    public Single<RestaurantDetailsResponse> putRestaurantSubscribeApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_RESTAURANT_DETAILS + restaurantId +
                "/subscribe")
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantDetailsResponse.class);
    }

    @Override
    public Single<MenuResponse> getRestaurantMenuApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_MENU + restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(MenuResponse.class);
    }

    @Override
    public Single<DailyMenuResponse> getRestaurantDailyMenuApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_DAILY_MENU + restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(DailyMenuResponse.class);
    }

    @Override
    public Single<RestaurantRatingResponse> getRestaurantRatingApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_RATING + restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantRatingResponse.class);
    }

    public Single<RestaurantFilterResponse> getRestaurantFilterApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_FILTER)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantFilterResponse.class);
    }

    @Override
    public Single<NotificationResponse> getNotificationsApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_NOTIFICATIONS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(NotificationResponse.class);
    }

    @Override
    public Single<SettingsResponse> getSettingsApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_SETTINGS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(SettingsResponse.class);
    }

    @Override
    public Single<UserDetailsResponse> getUserDetailsApiCall(Long userId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_USER_DETAILS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(UserDetailsResponse.class);
    }

    private static byte[] getScaledImage(byte[] originalImage, int newWidth, int newHeight) {
        // PNG has not losses, it just ignores this field when compressing
        final int COMPRESS_QUALITY = 0;

        // Get the bitmap from byte array since, the bitmap has the the resize function
        Bitmap bitmapImage = (BitmapFactory.decodeByteArray(originalImage, 0, originalImage.length));

        float aspectRatio = (float)bitmapImage.getWidth() / (float)bitmapImage.getHeight() ;
        newWidth = (int)(aspectRatio * ((float)newHeight));

        // New bitmap with the correct size, may not return a null object
        Bitmap mutableBitmapImage = Bitmap.createScaledBitmap(bitmapImage, newWidth, newHeight,
                false);

        // Get the byte array from tbe bitmap to be returned
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mutableBitmapImage.compress(Bitmap.CompressFormat.PNG, 0 , outputStream);

        if (mutableBitmapImage != bitmapImage) {
            mutableBitmapImage.recycle();
        } // else they are the same, just recycle once

        bitmapImage.recycle();
        return outputStream.toByteArray();
    }

    @Override
    public Single<UserDetailsResponse> putUserImageUpdateRaw(byte[] imageBytes) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_USER_UPLOAD_IMAGE_RAW)
                .setContentType("application/octet-stream")
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addByteBody(getScaledImage(imageBytes, IMAGE_HEIGHT, IMAGE_HEIGHT))
                .build()
                .getObjectSingle(UserDetailsResponse.class);
    }



    @Override
    public Single<RestaurantDetailsResponse> putRestaurantImageUpdateRaw(byte[] imageBytes) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RESTAURANT_UPLOAD_IMAGE_RAW)
                .setContentType("application/octet-stream")
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addByteBody(getScaledImage(imageBytes, IMAGE_HEIGHT, IMAGE_HEIGHT))
                .build()
                .getObjectSingle(RestaurantDetailsResponse.class);
    }

    @Override
    public Single<RestaurantPromotionsResponse> putPromotionImageUpdateRaw(byte[] imageBytes,
                                                                           Long promotionId) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PROMOTION_UPLOAD_IMAGE_RAW + promotionId + "/imageraw/")
                .setContentType("application/octet-stream")
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addByteBody(getScaledImage(imageBytes, IMAGE_HEIGHT, IMAGE_HEIGHT))
                .build()
                .getObjectSingle(RestaurantPromotionsResponse.class);
    }

    @Override
    public Single<DishDetailsResponse> putDishImageUpdateRaw(byte[] imageBytes, Long dishId) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DISH_UPLOAD_IMAGE_RAW + dishId +
                "/imageraw/")
                .setContentType("application/octet-stream")
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addByteBody(getScaledImage(imageBytes, IMAGE_HEIGHT, IMAGE_HEIGHT))
                .build()
                .getObjectSingle(DishDetailsResponse.class);
    }

    @Override
    public Single<RestaurantRatingResponse> getDishRatingApiCall(Long id) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_DISH_RATING + id + "/raiting")
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantRatingResponse.class);
    }

    public Single<RestaurantDetailsResponse> putRestaurantDetailsApiCall(RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_MANAGER_UPDATE_RESTAURANT_DETAILS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(restaurantDetails)
                .build()
                .getObjectSingle(RestaurantDetailsResponse.class);
    }

    @Override
    public Single<MenuResponse> putMenuApiCall(MenuResponse.Menu menu) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_MANAGER_UPDATE_MENU)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(menu)
                .build()
                .getObjectSingle(MenuResponse.class);
    }

    @Override
    public Single<RestaurantCookResponse> getRestaurantCookApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_COOK + restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantCookResponse.class);
    }

    @Override
    public Single<RestaurantDishesResponse> getRestaurantDishesApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_DISHES)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantDishesResponse.class);
    }

    @Override
    public Single<MealResponse> getMealApiCall(Long mealId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MEAL + mealId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(MealResponse.class);
    }

    @Override
    public Single<DishDetailsResponse> getDishDetailsApiCall(Long dishId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_DISH_DETAILS + dishId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(DishDetailsResponse.class);
    }

    @Override
    public Single<UserDetailsResponse> putUserImageUpdate(File imageBytes) {

        byte[] bytes = "Vlada Indjic je jedan veliki doktor zanata svoga: ".getBytes();
        Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_USER_UPLOAD_IMAGE + "kurac")
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addByteBody(bytes)
                .build()
                .getObjectSingle(LogoutResponse.class);

//                .addMultipartParameter("key", "value")

//        return Rx2AndroidNetworking.
//                upload(ApiEndPoint.ENDPOINT_USER_UPLOAD_IMAGE)
//                .addHeaders(mApiHeader.getProtectedApiHeader())
//                .addMultipartFile("file", imageBytes)
////                .addMultipartParameter("key", "value")
//                .setPriority(Priority.HIGH)
//                .build()
//                .setUploadProgressListener(new UploadProgressListener() {
//                    @Override
//                    public void onProgress(long bytesUploaded, long totalBytes) {
//                        // do anything with progress
//                        System.out.println("**********Uploaduje se: " + bytesUploaded + " " + totalBytes);
//                    }
//                }).getObjectSingle(UserDetailsResponse.class);
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                        System.out.println("**********Kao sve je u redu: " + response.toString());
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                    }
//                });


        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_USER_DETAILS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(UserDetailsResponse.class);


    }

    public Single<Double> rateRestaurant(Long restaurantid, RestaurantScoreRequest restaurantScoreRequest) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RESTAURANT_RATE + restaurantid)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(restaurantScoreRequest)
                .build()
                .getObjectSingle(Double.class);
    }

    @Override
    public Single<Double> rateDish(Long dishId, RestaurantScoreRequest scoreRequest) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RATE_DISH + dishId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(scoreRequest)
                .build()
                .getObjectSingle(Double.class);
    }

    @Override
    public Single<RestaurantRatingResponse> postComment(Long restaurantId, CommentRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMMENT_RESTAURANT + restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(RestaurantRatingResponse.class);
    }

    @Override
    public Single<RestaurantRatingResponse> leaevComment(Long dishId, CommentRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMMENT_DISH + dishId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(RestaurantRatingResponse.class);
    }

    @Override
    public Single<RestaurantRatingResponse> voteComment(Long id, ComentVoteRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMMENT_RESTAURANT_VOTE + id)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(RestaurantRatingResponse.class);
    }

    @Override
    public Single<RestaurantRatingResponse> voteCommentDish(Long id, ComentVoteRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_COMMENT_DISH_VOTE + id)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(RestaurantRatingResponse.class);
    }

    @Override
    public Single<AllKitchensResponse> getAllKitchensApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_KITCHENS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(AllKitchensResponse.class);
    }

    @Override
    public Single<AllKitchensResponse> getAllKitchensForRestaurant(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_KITCHENS + restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(AllKitchensResponse.class);
    }

    @Override
    public Single<RestaurantCookResponse> deleteDish(Long id) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_COOK_DELETE + id)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantCookResponse.class);
    }

    @Override
    public Single<RestaurantPromotionsResponse> deletePromotion(Long promotionId) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_PROMOTION_DELETE + promotionId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantPromotionsResponse.class);
    }

    @Override
    public Single<DailyMenuResponse> deleteMeal(Long mealId) {
        return Rx2AndroidNetworking.delete(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_DAILY_MENU_DISH_DELETE + mealId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(DailyMenuResponse.class);
    }

    @Override
    public Single<PromotionDetailsResponse> createPromotion(PromotionDetailsResponse.Promotion promotion) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_PROMOTION_CREATE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(promotion)
                .build()
                .getObjectSingle(PromotionDetailsResponse.class);
    }

    @Override
    public Single<RestaurantPromotionsResponse> updatePromotion(Long promotionId, PromotionDetailsResponse.Promotion promotion) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_PROMOTION_CREATE + "/" + promotionId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(promotion)
                .build()
                .getObjectSingle(RestaurantPromotionsResponse.class);
    }

    @Override
    public Single<DishDetailsResponse> addDish(Long restaurantId, DishRequestDto requestData) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_COOK_DELETE+restaurantId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(requestData)
                .build()
                .getObjectSingle(DishDetailsResponse.class);
    }

    @Override
    public Single<RestaurantCookResponse> updateDish(Long dishId, DishRequestDto requestData) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_MANAGER_RESTAURANT_COOK_DELETE + dishId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(requestData)
                .build()
                .getObjectSingle(RestaurantCookResponse.class);
    }

    @Override
    public Single<DailyMenuResponse> addMeal(Long id, MealResponse.MealDetails data) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_RESTAURANT_MEAL + id)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(data)
                .build()
                .getObjectSingle(DailyMenuResponse.class);
    }

    @Override
    public Single<DailyMenuResponse> updateMeal(Long mealId, MealResponse.MealDetails data) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_RESTAURANT_MEAL_UPDATE+mealId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(data)
                .build()
                .getObjectSingle(DailyMenuResponse.class);
    }

    @Override
    public Single<UserDetailsResponse> putUserDetailsUpdate(UpdateUserDetailsRequest request) {
            return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_USER_DETAILS)
                    .addHeaders(mApiHeader.getProtectedApiHeader())
                    .addApplicationJsonBody(request)
                    .build()
                    .getObjectSingle(UserDetailsResponse.class);
    }

    @Override
    public Single<UserDetailsResponse> putUserDetailsPassword(ChangePasswordRequest request) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_USER_PASSWORD)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addApplicationJsonBody(request)
                .build()
                .getObjectSingle(UserDetailsResponse.class);
    }
}

