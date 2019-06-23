package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionDetailsResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private PromotionDetailsResponse.Promotion data;


    public PromotionDetailsResponse() {
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

    public Promotion getData() {
        return data;
    }

    public void setData(Promotion data) {
        this.data = data;
    }

    public static class Promotion {

        @Expose
        @SerializedName("id")
        private Long id;

        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("image_url")
        private String imageUrl;

        @Expose
        @SerializedName("sub_title")
        private String subTitle;

        @Expose
        @SerializedName("details")
        private String details;

        @Expose
        @SerializedName("price")
        private String price;

        @Expose
        @SerializedName("duration")
        private String duration;


        public Promotion() {
        }

        public Promotion(Long id, String title, String imageUrl, String subTitle, String details,
                         String price, String duration) {
            this.id = id;
            this.title = title;
            this.imageUrl = imageUrl;
            this.subTitle = subTitle;
            this.details = details;
            this.price = price;
            this.duration = duration;
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }

}
