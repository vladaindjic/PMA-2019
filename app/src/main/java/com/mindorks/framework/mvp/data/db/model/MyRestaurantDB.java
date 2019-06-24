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
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 08/12/16.
 */

@Entity(nameInDb = "my_restaurant_db")
public class MyRestaurantDB {

    @Expose
    @SerializedName("id_db")
    @Id
    private Long idDb;

    // ovo je id u remote bazi
    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("image_url")
    private String imageUrl;

    @Expose
    @SerializedName("latitude")
    private Double latitude;
    @Expose
    @SerializedName("longitude")
    private Double longitude;
    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("delivery")
    private boolean delivery;

    @Expose
    @SerializedName("work_time")
    private String workTime;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("subscribed")
    private boolean subscribed;

    @ToMany(referencedJoinProperty = "myRestaurantId")
    private List<KitchenDB> kitchenDBList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1038294401)
    private transient MyRestaurantDBDao myDao;

    @Generated(hash = 2080046440)
    public MyRestaurantDB(Long idDb, Long id, String name, String imageUrl,
            Double latitude, Double longitude, String address, boolean delivery,
            String workTime, String phone, String email, boolean subscribed) {
        this.idDb = idDb;
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.delivery = delivery;
        this.workTime = workTime;
        this.phone = phone;
        this.email = email;
        this.subscribed = subscribed;
    }

    @Generated(hash = 357855025)
    public MyRestaurantDB() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public boolean getDelivery() {
        return this.delivery;
    }

    public boolean getSubscribed() {
        return this.subscribed;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1236474045)
    public List<KitchenDB> getKitchenDBList() {
        if (kitchenDBList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            KitchenDBDao targetDao = daoSession.getKitchenDBDao();
            List<KitchenDB> kitchenDBListNew = targetDao
                    ._queryMyRestaurantDB_KitchenDBList(idDb);
            synchronized (this) {
                if (kitchenDBList == null) {
                    kitchenDBList = kitchenDBListNew;
                }
            }
        }
        return kitchenDBList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1156912656)
    public synchronized void resetKitchenDBList() {
        kitchenDBList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    // vi3: dodao sam da mogu lakse da pravim obicne Restaurant-e
    public RestaurantsResponse.Restaurant getRestaurantFromMyRestaurantDB() {
        RestaurantsResponse.Restaurant restaurant = new RestaurantsResponse.Restaurant();
        restaurant.setId(this.id);
        restaurant.setImageUrl(this.imageUrl);
        restaurant.setLatitude(this.latitude);
        restaurant.setLongitude(this.longitude);
        restaurant.setAddress(this.address);
        restaurant.setName(this.name);
        return restaurant;
    }

    // vi3: dodao sam da mogu lakse da pravim obicne Restaurant-e
    public RestaurantDetailsResponse.RestaurantDetails getRestaurantDetailsFromMyRestaurantDB() {
        RestaurantDetailsResponse.RestaurantDetails restaurantDetails = new RestaurantDetailsResponse.RestaurantDetails();
        restaurantDetails.setId(this.id);
        restaurantDetails.setImageUrl(this.imageUrl);
        restaurantDetails.setLatitude(this.latitude);
        restaurantDetails.setLongitude(this.longitude);
        restaurantDetails.setAddress(this.address);
        restaurantDetails.setName(this.name);
        restaurantDetails.setWorkTime(this.workTime);
        restaurantDetails.setDelivery(this.delivery);
        restaurantDetails.setPhone(this.phone);
        restaurantDetails.setSubscribed(this.subscribed);
        restaurantDetails.setEmail(this.email);
        // postaviti kuhinje
        List<RestaurantDetailsResponse.Kitchen> kitchenList = new ArrayList<>();
        for(KitchenDB kdb: this.getKitchenDBList()) {
            kitchenList.add(kdb.getKitchenFromKitchenDB());
        }
        restaurantDetails.setKitchens(kitchenList);

        return restaurantDetails;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1456324656)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMyRestaurantDBDao() : null;
    }

}