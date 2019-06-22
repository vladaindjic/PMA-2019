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

import com.mindorks.framework.mvp.BuildConfig;

/**
 * Created by amitshekhar on 01/02/17.
 */

public final class ApiEndPoint {

    public static final String ENDPOINT_GOOGLE_LOGIN = BuildConfig.BASE_URL
            + "/588d14f4100000a9072d2943";

    public static final String ENDPOINT_FACEBOOK_LOGIN = BuildConfig.BASE_URL
            + "/588d15d3100000ae072d2944";

//    public static final String ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL
//            + "/588d15f5100000a8072d2945";

    public static final String ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL
            + "/user/login";

    // sucessfully log in
    public static final String ENDPOINT_SERVER_USER_REGISTRATION = BuildConfig.BASE_URL
            + "/user/registration";

    public static final String ENDPOINT_LOGOUT = BuildConfig.BASE_URL
            + "/588d161c100000a9072d2946";

    public static final String ENDPOINT_BLOG = BuildConfig.BASE_URL
            + "/5926ce9d11000096006ccb30";

    public static final String ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL
            + "/5926c34212000035026871cd";

    public static final String ENDPOINT_RESTAURANTS = BuildConfig.BASE_URL
            + "/restaurants/";
    public static final String ENDPOINT_SUBSCRIPTIONS = BuildConfig.BASE_URL
            + "/restaurants/my-restaurants";

    public static final String ENDPOINT_PROMOTION_DETAILS = BuildConfig.BASE_URL
            + "/promotion/";


    public static final String ENDPOINT_RESTAURANT_PROMOTIONS = BuildConfig.BASE_URL +
            "/promotion/restaurant/";

    public static final String ENDPOINT_RESTAURANT_DETAILS = BuildConfig.BASE_URL
            + "/restaurants/";

    public static final String ENDPOINT_KITCHENS = BuildConfig.BASE_URL
            + "/kitchen/";


    public static final String ENDPOINT_RESTAURANT_MENU = BuildConfig.BASE_URL
            + "/menu/";

    public static final String ENDPOINT_RESTAURANT_DAILY_MENU = BuildConfig.BASE_URL
            + "/dailymenu/";


    public static final String ENDPOINT_RESTAURANT_RATING = BuildConfig.BASE_URL
            + "/restaurantscore/";

    public static final String ENDPOINT_RESTAURANT_FILTER = BuildConfig.BASE_URL
            + "/5cb1d31c330000c9255720ef";

    public static final String ENDPOINT_NOTIFICATIONS = BuildConfig.BASE_URL
            + "/5cb35e20300000d124a78e0c";

    public static final String ENDPOINT_SETTINGS = BuildConfig.BASE_URL
            + "/5cb3ac40330000840411b6f3";

    public static final String ENDPOINT_USER_DETAILS = BuildConfig.BASE_URL
            + "/user/";
    public static final String ENDPOINT_USER_UPLOAD_IMAGE = BuildConfig.BASE_URL
            + "/user/image";

    public static final String ENDPOINT_USER_UPLOAD_IMAGE_RAW = BuildConfig.BASE_URL
            + "/user/imageraw";

    public static final String ENDPOINT_RESTAURANT_UPLOAD_IMAGE_RAW = BuildConfig.BASE_URL
            + "/restaurants/imageraw";


    public static final String ENDPOINT_DISH_RATING = BuildConfig.BASE_URL
            + "/dish/";


    public static final String ENDPOINT_DISH_DETAILS = BuildConfig.BASE_URL
            + "/dish/";

    public static final String ENDPOINT_MEAL = BuildConfig.BASE_URL
            + "/meal/";

    //    http://www.mocky.io/v2/5cb1d31c330000c9255720ef

    // manager endpoints
    public static final String ENDPOINT_MANAGER_UPDATE_RESTAURANT_DETAILS = BuildConfig.BASE_URL
            + "/restaurants/update";

    // manager endpoints
    public static final String ENDPOINT_MANAGER_UPDATE_MENU = BuildConfig.BASE_URL
            + "/menu";


    public static final String ENDPOINT_MANAGER_RESTAURANT_COOK = BuildConfig.BASE_URL
            + "/dish/restaurant/";

    public static  final String ENDPOINT_MANAGER_RESTAURANT_COOK_DELETE = BuildConfig.BASE_URL
            + "/dish/";

    // Sva jela jednog restorana
    public static final String ENDPOINT_MANAGER_RESTAURANT_DISHES = BuildConfig.BASE_URL
            + "/5cb72a0e320000510ecd4964";


    public static final String ENDPOINT_RESTAURANT_RATE = BuildConfig.BASE_URL
            + "/restaurantscore/";


    public static final String ENDPOINT_RATE_DISH = BuildConfig.BASE_URL
            + "/dishscore/";


    public static final String ENDPOINT_COMMENT_RESTAURANT = BuildConfig.BASE_URL
            + "/restaurantcomment/";

    public static final String ENDPOINT_COMMENT_DISH = BuildConfig.BASE_URL
            + "/dishcomment/";


    public static final String ENDPOINT_COMMENT_RESTAURANT_VOTE = BuildConfig.BASE_URL
            + "/restaurantcommentscore/";

    public static final String ENDPOINT_COMMENT_DISH_VOTE = BuildConfig.BASE_URL
            + "/dishcommentscore/";




    public static final String ENDPOINT_MANAGER_RESTAURANT_PROMOTION_DELETE = BuildConfig.BASE_URL
            +"/promotion/";

    public static final String ENDPOINT_MANAGER_RESTAURANT_PROMOTION_CREATE = BuildConfig.BASE_URL
            +"/promotion";

    public static final String ENDPOINT_MANAGER_RESTAURANT_DAILY_MENU_DISH_DELETE = BuildConfig.BASE_URL+
            "/dailymenu/";
    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
