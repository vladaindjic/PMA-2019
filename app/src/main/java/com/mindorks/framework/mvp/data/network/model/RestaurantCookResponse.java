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
            @SerializedName("restaurant_id")
            Long restaurantId;
            @Expose
            @SerializedName("id")
            private Long id;
            @Expose
            @SerializedName("name")
            private String restaurantCookItemData;
            @Expose
            @SerializedName("img_url")
            private String imgUrl;
            @Expose
            @SerializedName("price")
            private Double price;

            public RestaurantCookItem(Long restaurantId, Long id, String restaurantCookItemData, String imgUrl, Double price) {
                this.restaurantId = restaurantId;
                this.id = id;
                this.restaurantCookItemData = restaurantCookItemData;
                this.imgUrl = imgUrl;
                this.price = price;
            }

            public RestaurantCookItem(Long id) {
                this.id = id;
            }

            public RestaurantCookItem(Long id, String restaurantCookItemData) {
                this.id = id;
                this.restaurantCookItemData = restaurantCookItemData;
            }

            public RestaurantCookItem(Long id, String restaurantCookItemData, Long restaurantId) {
                this.id = id;
                this.restaurantCookItemData = restaurantCookItemData;
                this.restaurantId = restaurantId;
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

            public String getRestaurantCookItemData() {
                return restaurantCookItemData;
            }

            public void setRestaurantCookItemData(String restaurantCookItemData) {
                this.restaurantCookItemData = restaurantCookItemData;
            }

            public Long getRestaurantId() {
                return restaurantId;
            }

            public void setRestaurantId(Long restaurantId) {
                this.restaurantId = restaurantId;
            }
        }
    }
}

