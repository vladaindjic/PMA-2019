package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.framework.mvp.data.db.model.KitchenDB;

import java.util.List;

public class RestaurantDetailsResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private RestaurantDetails data;

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

    public RestaurantDetails getData() {
        return data;
    }

    public void setData(RestaurantDetails data) {
        this.data = data;
    }

    public static class RestaurantDetails {
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
        private List<Kitchen> kitchens;

        @Expose
        @SerializedName("subscribed")
        private boolean subscribed;

        @Expose
        @SerializedName("latitude")
        private Double latitude;
        @Expose
        @SerializedName("longitude")
        private Double longitude;


        public RestaurantDetails() {
        }

        public RestaurantDetails(Long id, String name, String imageUrl, String address,
                                 boolean delivery, String workTime, String phone, String email,
                                 List<Kitchen> kitchens, boolean subscribed, Double latitude,
                                 Double longitude) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
            this.address = address;
            this.delivery = delivery;
            this.workTime = workTime;
            this.phone = phone;
            this.email = email;
            this.kitchens = kitchens;
            this.subscribed = subscribed;
            this.latitude = latitude;
            this.longitude = longitude;
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

        public List<Kitchen> getKitchens() {
            return kitchens;
        }

        public void setKitchens(List<Kitchen> kitchens) {
            this.kitchens = kitchens;
        }

        public boolean getSubscribed() {
            return subscribed;
        }

        public void setSubscribed(boolean subscribed) {
            this.subscribed = subscribed;
        }

        public boolean isSubscribed() {
            return subscribed;
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
    }

    public static class Kitchen {
        @Expose
        @SerializedName("id")
        private Long id;
        @Expose
        @SerializedName("name")
        private String name;

        public Kitchen() {
        }

        public Kitchen(Long id, String name) {
            this.id = id;
            this.name = name;
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

        public KitchenDB getKitchenDB() {
            KitchenDB kitchenDB = new KitchenDB();
            // nema id za bazu
            kitchenDB.setIdDb(null);
            kitchenDB.setId(this.id);
            kitchenDB.setName(this.name);
            // restoran postavljamo nesto kasnije
            return kitchenDB;
        }
    }

}
