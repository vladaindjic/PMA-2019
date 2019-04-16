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

import io.reactivex.Single;

/**
 * Created by janisharali on 27/01/17.
 */

public interface ApiHelper {

    ApiHeader getApiHeader();

    Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Single<UserRegistrationResponse> doUserRegistrationApiCall(UserRegistrationRequest request);


    Single<LogoutResponse> doLogoutApiCall();

    Single<BlogResponse> getBlogApiCall();

    Single<OpenSourceResponse> getOpenSourceApiCall();

    Single<RestaurantsResponse> getRestaurantsApiCall();

    // TODO vi3: treba dodati i proveru koji user salji ili to sa servera gledati po jwt-u
    public Single<RestaurantsResponse> getSubscriptionsApiCall();

    //TODO Milan: Dodati id restorana
    Single<RestaurantPromotionsResponse> getRestaurantPromotions();

    //TODO Milan: Dodati id promocije
    Single<PromotionDetailsResponse> getPromotionDetails();

    Single<RestaurantDetailsResponse> getRestaurantDetailsApiCall(Long restaurantId);

    Single<RestaurantRatingResponse> getRestaurantRatingApiCall(Long restaurantId);

    Single<RestaurantFilterResponse> getRestaurantFilterApiCall();

    Single<NotificationResponse> getNotificationsApiCall();

    Single<SettingsResponse> getSettingsApiCall();

    Single<UserDetailsResponse> getUserDetailsApiCall(Long userId);
}
