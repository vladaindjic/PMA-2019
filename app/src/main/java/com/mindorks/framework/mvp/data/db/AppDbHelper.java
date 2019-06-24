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

import com.mindorks.framework.mvp.data.db.model.DaoMaster;
import com.mindorks.framework.mvp.data.db.model.DaoSession;
import com.mindorks.framework.mvp.data.db.model.KitchenDB;
import com.mindorks.framework.mvp.data.db.model.KitchenOption;
import com.mindorks.framework.mvp.data.db.model.MyRestaurantDB;
import com.mindorks.framework.mvp.data.db.model.MyRestaurantDBDao;
import com.mindorks.framework.mvp.data.db.model.Notification;
import com.mindorks.framework.mvp.data.db.model.NotificationDao;
import com.mindorks.framework.mvp.data.db.model.Option;
import com.mindorks.framework.mvp.data.db.model.Question;
import com.mindorks.framework.mvp.data.db.model.User;
import com.mindorks.framework.mvp.data.db.model.UserFilter;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Created by janisharali on 08/12/16.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertUser(final User user) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getUserDao().insert(user);
            }
        });
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                return mDaoSession.getUserDao().loadAll();
            }
        });
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return Observable.fromCallable(new Callable<List<Question>>() {
            @Override
            public List<Question> call() throws Exception {
                return mDaoSession.getQuestionDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getQuestionDao().count() > 0);
            }
        });
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mDaoSession.getOptionDao().count() > 0);
            }
        });
    }

    @Override
    public Observable<Boolean> saveQuestion(final Question question) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getQuestionDao().insert(question);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveOption(final Option option) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getOptionDao().insertInTx(option);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveQuestionList(final List<Question> questionList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getQuestionDao().insertInTx(questionList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveOptionList(final List<Option> optionList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getOptionDao().insertInTx(optionList);
                return true;
            }
        });
    }

    @Override
    public Observable<List<Notification>> getAllNotification() {
        return Observable.fromCallable(new Callable<List<Notification>>(){

            @Override
            public List<Notification> call() throws Exception {
                return mDaoSession.getNotificationDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> saveNotification(final Notification notification) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getNotificationDao().insertInTx(notification);
                return true;
            }
        });
    }

    @Override
    public Observable<Long> saveUserFilter(final UserFilter userFilter) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getUserFilterDao().insert(userFilter);
            }
        });
    }

    @Override
    public Observable<Boolean> saveKitchenOption(final KitchenOption kitchenOption) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getKitchenOptionDao().insertInTx(kitchenOption);
                return true;
            }
        });
    }

    @Override
    public Observable<UserFilter> getUserFilter(final long id) {
        return Observable.fromCallable(new Callable<UserFilter>() {
            @Override
            public UserFilter call() throws Exception {
                return mDaoSession.getUserFilterDao().load(id);
            }
        });
    }

    @Override
    public Observable<List<MyRestaurantDB>> getAllMyRestaurants() {
        return Observable.fromCallable(new Callable<List<MyRestaurantDB>>() {
            @Override
            public List<MyRestaurantDB> call() throws Exception {
                return mDaoSession.getMyRestaurantDBDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAllMyRestaurants() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getMyRestaurantDBDao().deleteAll();
                return true;
            }
        });
    }

    @Override
    public Observable<MyRestaurantDB> getMyRestaurant(final long id) {
        return Observable.fromCallable(new Callable<MyRestaurantDB>() {
            @Override
            public MyRestaurantDB call() throws Exception {
                return mDaoSession.getMyRestaurantDBDao().load(id);
            }
        });
    }

    @Override
    public Observable<MyRestaurantDB> getMyRestaurantByRemoteDatabaseId(final long remoteDatabaseId) {
        return Observable.fromCallable(new Callable<MyRestaurantDB>() {
            @Override
            public MyRestaurantDB call() throws Exception {
                List<MyRestaurantDB> myRestaurantDBList =
                        mDaoSession.getMyRestaurantDBDao().queryBuilder().where(MyRestaurantDBDao.Properties.Id.eq(remoteDatabaseId)).list();
                if (myRestaurantDBList != null && myRestaurantDBList.size() > 0)
                    return myRestaurantDBList.get(0);
                return null;
            }
        });
    }

    @Override
    public Observable<Long> saveMyRestaurant(final MyRestaurantDB myRestaurantDB) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getMyRestaurantDBDao().insert(myRestaurantDB);
            }
        });
    }

    @Override
    public Observable<Long> saveKitchenDB(final KitchenDB kitchenDB) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getKitchenDBDao().insert(kitchenDB);
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAllKitchensDB() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mDaoSession.getKitchenDBDao().deleteAll();
                return true;
            }
        });
    }
}
