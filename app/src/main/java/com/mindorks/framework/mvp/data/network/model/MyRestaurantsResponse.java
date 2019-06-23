package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.framework.mvp.data.db.model.MyRestaurantDB;

import java.util.ArrayList;
import java.util.List;

public class MyRestaurantsResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<MyRestaurant> data;

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

    public List<MyRestaurant> getData() {
        return data;
    }

    public void setData(List<MyRestaurant> data) {
        this.data = data;
    }

    public static class MyRestaurant {
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
        @SerializedName("kitchens")
        private List<RestaurantDetailsResponse.Kitchen> kitchens;

        @Expose
        @SerializedName("subscribed")
        private boolean subscribed;


        public MyRestaurant() {
        }

        public MyRestaurant(Long id, String name, String imageUrl) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public MyRestaurant(Long id, String name, String imageUrl, Double latitude, Double longitude) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public MyRestaurant(Long id, String name, String imageUrl, Double latitude, Double longitude, String address) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = address;
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

        public List<RestaurantDetailsResponse.Kitchen> getKitchens() {
            return kitchens;
        }

        public void setKitchens(List<RestaurantDetailsResponse.Kitchen> kitchens) {
            this.kitchens = kitchens;
        }

        public boolean isSubscribed() {
            return subscribed;
        }

        public void setSubscribed(boolean subscribed) {
            this.subscribed = subscribed;
        }

        public RestaurantsResponse.Restaurant getRestaurantFromMyRestaurant() {
            RestaurantsResponse.Restaurant restaurant = new RestaurantsResponse.Restaurant();

            restaurant.setId(this.id);
            restaurant.setName(this.name);
            restaurant.setAddress(this.address);
            restaurant.setLatitude(this.latitude);
            restaurant.setLongitude(this.longitude);
            restaurant.setImageUrl(this.imageUrl);

            return restaurant;
        }

        public MyRestaurantDB getMyRestaurantDBFromMyRestaurant() {
            MyRestaurantDB myRestaurantDB = new MyRestaurantDB();
            // nema id u lokalnoj bazi
            myRestaurantDB.setIdDb(null);

            myRestaurantDB.setId(this.id);
            myRestaurantDB.setAddress(this.address);
            myRestaurantDB.setDelivery(this.delivery);
            myRestaurantDB.setEmail(this.email);
            myRestaurantDB.setImageUrl(this.imageUrl);
            myRestaurantDB.setLatitude(this.latitude);
            myRestaurantDB.setLongitude(this.longitude);
            myRestaurantDB.setName(this.name);
            myRestaurantDB.setPhone(this.phone);
            myRestaurantDB.setSubscribed(this.subscribed);
            myRestaurantDB.setWorkTime(this.workTime);

            return myRestaurantDB;
        }
    }

    public List<RestaurantsResponse.Restaurant> getRestaurantsFromMyRestaurant() {
        List<RestaurantsResponse.Restaurant> restaurantList = new ArrayList<>();
        for (MyRestaurant mr : this.data) {
            restaurantList.add(mr.getRestaurantFromMyRestaurant());
        }
        return restaurantList;
    }

    public List<MyRestaurantDB> getMyRestaurantsDBFromMyRestaurant() {
        List<MyRestaurantDB> myRestaurantList = new ArrayList<>();
        for (MyRestaurant mr : this.data) {
            myRestaurantList.add(mr.getMyRestaurantDBFromMyRestaurant());
        }
        return myRestaurantList;
    }
}
