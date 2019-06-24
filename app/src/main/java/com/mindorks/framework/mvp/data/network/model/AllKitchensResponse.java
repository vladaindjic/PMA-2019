package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllKitchensResponse {
    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> data;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> getData() {
        return data;
    }

    public void setData(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> data) {
        this.data = data;
    }
}
