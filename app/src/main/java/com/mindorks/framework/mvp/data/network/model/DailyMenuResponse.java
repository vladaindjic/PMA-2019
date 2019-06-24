package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class DailyMenuResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("daily_menu")
    private DailyMenu data; // FIXME vi3: mozda ovo treba da vratim na dailyMenu

    public DailyMenuResponse() {
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

    public DailyMenu getData() {
        return data;
    }

    public void setData(DailyMenu data) {
        this.data = data;
    }

    public static class DailyMenu {
        @Expose
        @SerializedName("id")
        private Long id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("start_time")
        private Date startTime;
        @Expose
        @SerializedName("end_time")
        private Date endTime;
        @Expose
        @SerializedName("meals")
        private List<Meal> meals;
        @Expose
        @SerializedName("active_between")
        private String activeBetween;

        public DailyMenu() {
        }

        public DailyMenu(Long id, String name, Date startTime, Date endTime, List<Meal> meals, String activeBetween) {
            this.id = id;
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
            this.meals = meals;
            this.activeBetween = activeBetween;
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

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public List<Meal> getMeals() {
            return meals;
        }

        public void setMeals(List<Meal> meals) {
            this.meals = meals;
        }

        public String getActiveBetween() {
            return activeBetween;
        }

        public void setActiveBetween(String activeBetween) {
            this.activeBetween = activeBetween;
        }
    }

    public static class Meal {
        @Expose
        @SerializedName("id")
        private Long id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("price")
        private Double price;

        public Meal() {
        }

        public Meal(Long id, String name, String description, Double price) {
            this.id = id;
            this.name = name;
            this.description = description;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}
