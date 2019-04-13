package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MealResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("daily_menu")
    private DailyMenu dailyMenu;

    public MealResponse() {
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

    public DailyMenu getDailyMenu() {
        return dailyMenu;
    }

    public void setDailyMenu(DailyMenu dailyMenu) {
        this.dailyMenu = dailyMenu;
    }

    public static class DailyMenu {
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
        @Expose
        @SerializedName("start_time")
        private Date startTime;
        @Expose
        @SerializedName("end_time")
        private Date endTime;
        @Expose
        @SerializedName("meals")
        private List<Meal> meals;

        public DailyMenu() {
        }

        public DailyMenu(Long id, String name, Date startTime, Date endTime, List<Meal> meals) {
            this.id = id;
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
            this.meals = meals;
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
    }

    public static class Meal {
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
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("price")
        private Double price;
        @Expose
        @SerializedName("dishes")
        private List<MenuResponse.Dish> dishList;
        @Expose
        @SerializedName("nutritive_values")
        private List<DishDetailsResponse.NutritiveValue> nutritiveValues;

        public Meal() {
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

        public List<MenuResponse.Dish> getDishList() {
            return dishList;
        }

        public void setDishList(List<MenuResponse.Dish> dishList) {
            this.dishList = dishList;
        }

        public List<DishDetailsResponse.NutritiveValue> getNutritiveValues() {
            return nutritiveValues;
        }

        public void setNutritiveValues(List<DishDetailsResponse.NutritiveValue> nutritiveValues) {
            this.nutritiveValues = nutritiveValues;
        }
    }
}
