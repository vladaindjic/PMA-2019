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

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by janisharali on 28/01/17.
 */

@Entity(nameInDb = "kitchen_options")
public class KitchenOption {

    @Expose
    @SerializedName("id")
    @Id(autoincrement = true)
    private Long id;

    @Expose
    @SerializedName("kitchen_name")
    @Property(nameInDb = "kitchen_name")
    private String kitchenName;

    @Expose
    @SerializedName("checked")
    @Property(nameInDb = "checked")
    private boolean checked;

    @Expose
    @SerializedName("user_filter_id")
    @Property(nameInDb = "user_filter_id")
    private Long userFilterId;

    @Generated(hash = 680931590)
    public KitchenOption(Long id, String kitchenName, boolean checked,
            Long userFilterId) {
        this.id = id;
        this.kitchenName = kitchenName;
        this.checked = checked;
        this.userFilterId = userFilterId;
    }

    @Generated(hash = 538346944)
    public KitchenOption() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKitchenName() {
        return kitchenName;
    }

    public void setKitchenName(String kitchenName) {
        this.kitchenName = kitchenName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getUserFilterId() {
        return userFilterId;
    }

    public void setUserFilterId(Long userFilterId) {
        this.userFilterId = userFilterId;
    }

    public boolean getChecked() {
        return this.checked;
    }
}