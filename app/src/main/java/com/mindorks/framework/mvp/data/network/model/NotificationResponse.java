package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private NotificationResponse.Notifications data;

    public NotificationResponse(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public NotificationResponse(int httpStatusCode, String message, Notifications data) {
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

    public Notifications getData() {
        return data;
    }

    public void setData(Notifications data) {
        this.data = data;
    }



    public static class Notifications {
        @Expose
        @SerializedName("notifications")
        List<Notification> notifications;

        public Notifications(List<Notification> notifications) {
            this.notifications = notifications;
        }

        public List<Notification> getNotifications() {
            return notifications;
        }

        public void setNotifications(List<Notification> notifications) {
            this.notifications = notifications;
        }

        public Notifications() {
        }

        public static class Notification {
            @Expose
            @SerializedName("id")
            private Long id;

            @Expose
            @SerializedName("notificationData")
            private String notificationData;

            @Expose
            @SerializedName("restaurant_id")
            Long restaurantId;

            public Notification(Long id) {
                this.id = id;
            }

            public Notification(Long id, String notificationData) {
                this.id = id;
                this.notificationData = notificationData;
            }


            public Notification(Long id, String notificationData, Long restaurantId) {
                this.id = id;
                this.notificationData = notificationData;
                this.restaurantId = restaurantId;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getNotificationData() {
                return notificationData;
            }

            public void setNotificationData(String notificationData) {
                this.notificationData = notificationData;
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
