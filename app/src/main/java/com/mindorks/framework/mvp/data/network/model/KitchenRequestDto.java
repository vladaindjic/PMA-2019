package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class KitchenRequestDto {

    @Expose
    @SerializedName("name")
    private String name;

    public KitchenRequestDto() {
    }

    public KitchenRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
