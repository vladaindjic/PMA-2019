package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class NutritiveValueRequestDto {

    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("value")
    private Double value;
    @Expose
    @SerializedName("unit")
    private String unit;

    public NutritiveValueRequestDto() {
    }

    public NutritiveValueRequestDto(String name, Double value, String unit) {
        this.name = name;
        this.value = value;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
