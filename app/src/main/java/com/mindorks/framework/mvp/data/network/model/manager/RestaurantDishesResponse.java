package com.mindorks.framework.mvp.data.network.model.manager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;

import java.util.List;

public class RestaurantDishesResponse {
    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<MenuResponse.Dish> dishList;

    public RestaurantDishesResponse() {
    }

    public RestaurantDishesResponse(int httpStatusCode, String message, List<MenuResponse.Dish> dishList) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.dishList = dishList;
    }

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

    public List<MenuResponse.Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<MenuResponse.Dish> dishList) {
        this.dishList = dishList;
    }
}
