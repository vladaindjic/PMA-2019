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

package com.mindorks.framework.mvp.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by janisharali on 28/01/17.
 */

@Entity(nameInDb = "kitchen_db")
public class KitchenDB {

    @Expose
    @SerializedName("id_db")
    @Id
    private Long idDb;

    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("my_restaurant_d")
    @Property(nameInDb = "my_restaurant_d")
    private Long myRestaurantId;

    @Generated(hash = 1497770006)
    public KitchenDB(Long idDb, Long id, String name, Long myRestaurantId) {
        this.idDb = idDb;
        this.id = id;
        this.name = name;
        this.myRestaurantId = myRestaurantId;
    }

    @Generated(hash = 1612758231)
    public KitchenDB() {
    }

    public Long getIdDb() {
        return idDb;
    }

    public void setIdDb(Long idDb) {
        this.idDb = idDb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMyRestaurantId() {
        return myRestaurantId;
    }

    public void setMyRestaurantId(Long myRestaurantId) {
        this.myRestaurantId = myRestaurantId;
    }

    public RestaurantDetailsResponse.Kitchen getKitchenFromKitchenDB() {
        RestaurantDetailsResponse.Kitchen kitchen = new RestaurantDetailsResponse.Kitchen();
        kitchen.setId(this.id);
        kitchen.setName(this.name);
        return kitchen;
    }
}