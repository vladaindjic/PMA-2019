package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantCookResponse {


    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private RestaurantCookResponse.RestaurantCook data;

    public RestaurantCookResponse(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public RestaurantCookResponse(int httpStatusCode, String message, RestaurantCookResponse.RestaurantCook data) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = data;
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

    public RestaurantCookResponse.RestaurantCook getData() {
        return data;
    }

    public void setData(RestaurantCookResponse.RestaurantCook data) {
        this.data = data;
    }


    public static class RestaurantCook {
        @Expose
        @SerializedName("restaurant_cook")
        List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> restaurantCookItemList;

        public RestaurantCook(List<RestaurantCookItem> restaurantCookItemList) {
            this.restaurantCookItemList = restaurantCookItemList;
        }

        public RestaurantCook() {
        }

        public List<RestaurantCookItem> getRestaurantCookItemList() {
            return restaurantCookItemList;
        }

        public void setRestaurantCookItemList(List<RestaurantCookItem> restaurantCookItemList) {
            this.restaurantCookItemList = restaurantCookItemList;
        }

        public static class RestaurantCookItem {
            @Expose
            @SerializedName("id")
            private Long id;
            @Expose
            @SerializedName("name")
            private String name;
            @Expose
            @SerializedName("img_url")
            private String imgUrl;
            @Expose
            @SerializedName("price")
            private Double price;

            public RestaurantCookItem() {
            }

            public RestaurantCookItem(Long id, String name, String imgUrl, Double price) {
                this.id = id;
                this.name = name;
                this.imgUrl = imgUrl;
                this.price = price;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public Double getPrice() {
                return price;
            }

            public void setPrice(Double price) {
                this.price = price;
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
        }
    }
}

