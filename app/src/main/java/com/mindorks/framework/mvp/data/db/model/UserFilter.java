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

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by janisharali on 08/12/16.
 */

@Entity(nameInDb = "user_filter")
public class UserFilter {

    @Expose
    @SerializedName("id")
    @Id
    private Long id;

    @Expose
    @SerializedName("delivery")
    @Property(nameInDb = "delivery")
    private boolean delivery;

    @Expose
    @SerializedName("open")
    @Property(nameInDb = "open")
    private boolean open;

    @Expose
    @SerializedName("daily_menu")
    @Property(nameInDb = "daily_menu")
    private boolean dailyMenu;

    @Expose
    @SerializedName("distance")
    @Property(nameInDb = "distance")
    private double distance;


    @ToMany(referencedJoinProperty = "userFilterId")
    private List<KitchenOption> kitchenOptionList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1845930868)
    private transient UserFilterDao myDao;

    @Generated(hash = 349634050)
    public UserFilter(Long id, boolean delivery, boolean open, boolean dailyMenu,
            double distance) {
        this.id = id;
        this.delivery = delivery;
        this.open = open;
        this.dailyMenu = dailyMenu;
        this.distance = distance;
    }

    @Generated(hash = 643710719)
    public UserFilter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isDailyMenu() {
        return dailyMenu;
    }

    public void setDailyMenu(boolean dailyMenu) {
        this.dailyMenu = dailyMenu;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean getDelivery() {
        return this.delivery;
    }

    public boolean getOpen() {
        return this.open;
    }

    public boolean getDailyMenu() {
        return this.dailyMenu;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 231732993)
    public List<KitchenOption> getKitchenOptionList() {
        if (kitchenOptionList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            KitchenOptionDao targetDao = daoSession.getKitchenOptionDao();
            List<KitchenOption> kitchenOptionListNew = targetDao
                    ._queryUserFilter_KitchenOptionList(id);
            synchronized (this) {
                if (kitchenOptionList == null) {
                    kitchenOptionList = kitchenOptionListNew;
                }
            }
        }
        return kitchenOptionList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 49536983)
    public synchronized void resetKitchenOptionList() {
        kitchenOptionList = null;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1359516679)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserFilterDao() : null;
    }
}