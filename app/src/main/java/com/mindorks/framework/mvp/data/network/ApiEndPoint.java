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
            + "/5ca521393300004f002ea669";

    // sucessfully log in
//    public static final String ENDPOINT_SERVER_USER_REGISTRATION = BuildConfig.BASE_URL
//            + "/5ca4fec73300004f002ea5dd";
    // failed log in
    public static final String ENDPOINT_SERVER_USER_REGISTRATION = BuildConfig.BASE_URL
            + "/5ca5146033000050002ea630";

    public static final String ENDPOINT_LOGOUT = BuildConfig.BASE_URL
            + "/588d161c100000a9072d2946";

    public static final String ENDPOINT_BLOG = BuildConfig.BASE_URL
            + "/5926ce9d11000096006ccb30";

    public static final String ENDPOINT_OPEN_SOURCE = BuildConfig.BASE_URL
            + "/5926c34212000035026871cd";

    public static final String ENDPOINT_RESTAURANTS = BuildConfig.BASE_URL
            + "/5ca5f7c93300008f532eaab3";

    public static final String ENDPOINT_PROMOTION_DETAILS = BuildConfig.BASE_URL
            +"/5cacd9762f00005f003a93a9";


    public static final String ENDPOINT_RESTAURANT_PROMOTIONS = BuildConfig.BASE_URL +
            "/5cabe6073000002d17103280";

    public static final String ENDPOINT_RESTAURANT_DETAILS = BuildConfig.BASE_URL
            + "/5cabcb913000004c00103250";

    public static final String ENDPOINT_RESTAURANT_FILTER = BuildConfig.BASE_URL
            + "/5cb118753300003f0c571fd7";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
