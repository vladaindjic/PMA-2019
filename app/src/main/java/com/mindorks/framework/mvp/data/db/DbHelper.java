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

package com.mindorks.framework.mvp.data.db;

import com.mindorks.framework.mvp.data.db.model.KitchenDB;
import com.mindorks.framework.mvp.data.db.model.KitchenOption;
import com.mindorks.framework.mvp.data.db.model.MyRestaurantDB;
import com.mindorks.framework.mvp.data.db.model.Notification;
import com.mindorks.framework.mvp.data.db.model.Option;
import com.mindorks.framework.mvp.data.db.model.Question;
import com.mindorks.framework.mvp.data.db.model.User;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.MyRestaurantsResponse;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by janisharali on 08/12/16.
 */

public interface DbHelper {

    Observable<Long> insertUser(final User user);

    Observable<List<User>> getAllUsers();

    Observable<List<Question>> getAllQuestions();

    Observable<Boolean> isQuestionEmpty();

    Observable<Boolean> isOptionEmpty();

    Observable<Boolean> saveQuestion(Question question);

    Observable<Boolean> saveOption(Option option);

    Observable<Boolean> saveQuestionList(List<Question> questionList);

    Observable<Boolean> saveOptionList(List<Option> optionList);

    Observable<List<Notification>> getAllNotification();

    Observable<Boolean> saveNotification(Notification notification);

    Observable<Long> saveUserFilter(UserFilter userFilter);
    Observable<UserFilter> getUserFilter(long id);

    Observable<Boolean> saveKitchenOption(KitchenOption kitchenOption);

    // potrebno radi kesiranja restorana
    Observable<List<MyRestaurantDB>> getAllMyRestaurants();
    Observable<Boolean> deleteAllMyRestaurants();
    Observable<MyRestaurantDB> getMyRestaurant(long id);
    Observable<MyRestaurantDB> getMyRestaurantByRemoteDatabaseId(long remoteDatabaseId);
    Observable<Long> saveMyRestaurant(MyRestaurantDB myRestaurantDB);
    Observable<Long> saveKitchenDB(KitchenDB kitchenDB);
    Observable<Boolean> deleteAllKitchensDB();

}
