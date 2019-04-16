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

import com.mindorks.framework.mvp.data.network.model.BlogResponse;
import com.mindorks.framework.mvp.data.network.model.LoginRequest;
import com.mindorks.framework.mvp.data.network.model.LoginResponse;
import com.mindorks.framework.mvp.data.network.model.LogoutResponse;
import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.data.network.model.OpenSourceResponse;
import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationRequest;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by janisharali on 28/01/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

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
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<UserRegistrationResponse> doUserRegistrationApiCall(UserRegistrationRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_USER_REGISTRATION)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
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
    public Single<RestaurantsResponse> getRestaurantsApiCall() {
        // TODO vi3: dodati parametre za filterisanje, treba ubaciti i id korisnika koji salje
        // zahtev; to mozda moze i na serveru preko tokena da se gleda
        // i ustedu saobracaja
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANTS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantsResponse.class);
    }

    @Override
    public Single<RestaurantsResponse> getSubscriptionsApiCall() {
        // TODO vi3: dodati parametre za filterisanje, treba ubaciti i id korisnika koji salje
        // zahtev; to mozda moze i na serveru preko tokena da se gleda
        // i ustedu saobracaja
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_SUBSCRIPTIONS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantsResponse.class);
    }


    @Override
    public Single<RestaurantPromotionsResponse> getRestaurantPromotions() {
        //TODO milan: dodati id resorana cije promocije dobavljamo
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_PROMOTIONS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantPromotionsResponse.class);
    }

    @Override
    public Single<PromotionDetailsResponse> getPromotionDetails() {
        //TODO milan: dodati id resorana cije promocije dobavljamo
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PROMOTION_DETAILS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(PromotionDetailsResponse.class);
    }


    public Single<RestaurantDetailsResponse> getRestaurantDetailsApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_DETAILS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantDetailsResponse.class);
    }

    @Override
    public Single<RestaurantRatingResponse> getRestaurantRatingApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_RESTAURANT_RATING)
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

    @Override
    public Single<RestaurantRatingResponse> getDishRatingApiCall(Long restaurantId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_DISH_RATING)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(RestaurantRatingResponse.class);
    }
}

