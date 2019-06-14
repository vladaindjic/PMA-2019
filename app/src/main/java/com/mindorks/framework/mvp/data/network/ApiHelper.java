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

import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.AllKitchensResponse;
import com.mindorks.framework.mvp.data.network.model.BlogResponse;
import com.mindorks.framework.mvp.data.network.model.ComentVoteRequest;
import com.mindorks.framework.mvp.data.network.model.CommentRequest;
import com.mindorks.framework.mvp.data.network.model.DailyMenuResponse;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
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
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantScoreRequest;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationRequest;
import com.mindorks.framework.mvp.data.network.model.UserRegistrationResponse;
import com.mindorks.framework.mvp.data.network.model.manager.RestaurantDishesResponse;

import java.io.File;

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

    Single<RestaurantsResponse> getRestaurantsApiCall(FilterRestaurantRequest filterRestaurantRequest);

    // TODO vi3: treba dodati i proveru koji user salji ili to sa servera gledati po jwt-u
    public Single<RestaurantsResponse> getSubscriptionsApiCall();

    Single<RestaurantPromotionsResponse> getRestaurantPromotions(Long restaurantId);

    Single<PromotionDetailsResponse> getPromotionDetails(Long promotionId);

    Single<RestaurantDetailsResponse> getRestaurantDetailsApiCall(Long restaurantId);

    Single<RestaurantDetailsResponse> putRestaurantSubscribeApiCall(Long restaurantId);

    Single<MenuResponse> getRestaurantMenuApiCall(Long restaurantId);

    Single<DailyMenuResponse> getRestaurantDailyMenuApiCall(Long restaurantId);

    Single<RestaurantRatingResponse> getRestaurantRatingApiCall(Long restaurantId);

    Single<RestaurantFilterResponse> getRestaurantFilterApiCall();

    Single<NotificationResponse> getNotificationsApiCall();

    Single<SettingsResponse> getSettingsApiCall();

    Single<UserDetailsResponse> getUserDetailsApiCall(Long userId);

    Single<RestaurantRatingResponse> getDishRatingApiCall(Long restaurantId);
    // mozda bude trebalo da se pravi poseban request objekat, mada mislim da za sada nema potrebe
    Single<RestaurantDetailsResponse> putRestaurantDetailsApiCall(RestaurantDetailsResponse.RestaurantDetails restaurantDetails);

    Single<MenuResponse> putMenuApiCall(MenuResponse.Menu menu);

    Single<RestaurantCookResponse> getRestaurantCookApiCall(Long restaurantId);

    Single<RestaurantDishesResponse> getRestaurantDishesApiCall(Long restaurantId);


    Single<MealResponse> getMealApiCall(Long mealId);

    Single<DishDetailsResponse> getDishDetailsApiCall(Long dishId);

//    Single<UserDetailsResponse> putUserImageUpdate(byte[] imageBytes);
    Single<UserDetailsResponse> putUserImageUpdate(File imageBytes);
    Single<UserDetailsResponse> putUserImageUpdateRaw(byte[] imageBytes);

    Single<Double> rateRestaurant(Long restaurantid, RestaurantScoreRequest restaurantScoreRequest);

    Single<Double> rateDish(Long dishId, RestaurantScoreRequest scoreRequest);

    Single<RestaurantRatingResponse> postComment(Long restaurantId, CommentRequest request);


    Single<RestaurantRatingResponse> leaevComment(Long dishId,CommentRequest request);
    Single<RestaurantRatingResponse> voteComment(Long id, ComentVoteRequest request);

    Single<RestaurantRatingResponse>voteCommentDish(Long id, ComentVoteRequest request);

    Single<AllKitchensResponse> getAllKitchensApiCall();


}
