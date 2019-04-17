package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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
    @SerializedName("data")
    private MealDetails data;

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

    public MealDetails getData() {
        return data;
    }

    public void setData(MealDetails data) {
        this.data = data;
    }

    public static class MealDetails {
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

        public MealDetails() {
            dishList = new ArrayList<>();
        }

        public MealDetails(Long id, String name, Date startTime, Date endTime, String description, Double price, List<MenuResponse.Dish> dishList, List<DishDetailsResponse.NutritiveValue> nutritiveValues) {
            this.id = id;
            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
            this.description = description;
            this.price = price;
            this.dishList = dishList;
            this.nutritiveValues = nutritiveValues;
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
