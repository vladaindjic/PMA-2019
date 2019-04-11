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

package com.mindorks.framework.mvp.di.component;

import com.mindorks.framework.mvp.di.PerActivity;
import com.mindorks.framework.mvp.di.module.ActivityModule;
import com.mindorks.framework.mvp.ui.about.AboutFragment;
import com.mindorks.framework.mvp.ui.feed.FeedActivity;
import com.mindorks.framework.mvp.ui.feed.blogs.BlogFragment;
import com.mindorks.framework.mvp.ui.feed.opensource.OpenSourceFragment;
import com.mindorks.framework.mvp.ui.login.LoginActivity;
import com.mindorks.framework.mvp.ui.main.MainActivity;
import com.mindorks.framework.mvp.ui.main.rating.RateUsDialog;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurant.dailyMenu.UserRestaurantDailyMenuFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.details.UserRestaurantDetailsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.menu.UserRestaurantMenuFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.UserRestaurantPromotionsFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.details.PromotionDetailsActivity;
import com.mindorks.framework.mvp.ui.user.restaurant.ratings.UserRestaurantRatingFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.grid.RestaurantsGridFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.list.RestaurantsListFragment;
import com.mindorks.framework.mvp.ui.splash.SplashActivity;
import com.mindorks.framework.mvp.ui.userRegistration.UserRegistrationActivity;

import dagger.Component;

/**
 * Created by janisharali on 27/01/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(UserRegistrationActivity activity);

    void inject(UserRestaurantsActivity activity);

    void inject(UserRestaurantActivity activity);

    void inject(SplashActivity activity);

    void inject(FeedActivity activity);

    void inject(AboutFragment fragment);

    void inject(OpenSourceFragment fragment);

    void inject(BlogFragment fragment);

    void inject(RateUsDialog dialog);

    void inject(RestaurantsListFragment fragment);

    void inject(RestaurantsGridFragment fragment);

    void inject(UserRestaurantDetailsFragment fragment);

    void inject(UserRestaurantPromotionsFragment fragment);


    //Milan dodao
    void inject(PromotionDetailsActivity activity);

    void inject(UserRestaurantRatingFragment fragment);

    void inject(UserRestaurantMenuFragment fragment);

    void inject(UserRestaurantDailyMenuFragment fragment);
}
