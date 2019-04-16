package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DishDetailsResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private DishDetails data;

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

    public DishDetails getData() {
        return data;
    }

    public void setData(DishDetails data) {
        this.data = data;
    }


    public static class DishDetails {
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
        @SerializedName("kitchen")
        private Kitchen kitchen;
        @Expose
        @SerializedName("nutritive_values")
        private List<NutritiveValue> nutritiveValues;
        @Expose
        @SerializedName("image_url")
        private String imageUrl;

        public DishDetails() {
        }

        public DishDetails(Long id, String name, String description, Double price, Kitchen kitchen, List<NutritiveValue> nutritiveValues, String imageUrl) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.kitchen = kitchen;
            this.nutritiveValues = nutritiveValues;
            this.imageUrl = imageUrl;
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

        public Kitchen getKitchen() {
            return kitchen;
        }

        public void setKitchen(Kitchen kitchen) {
            this.kitchen = kitchen;
        }

        public List<NutritiveValue> getNutritiveValues() {
            return nutritiveValues;
        }

        public void setNutritiveValues(List<NutritiveValue> nutritiveValues) {
            this.nutritiveValues = nutritiveValues;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
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
    }

    public static class NutritiveValue {
        @Expose
        @SerializedName("id")
        private Long id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("value")
        private Double value;
        @Expose
        @SerializedName("unit")
        private String unit;

        public NutritiveValue() {
        }

        public NutritiveValue(Long id, String name, Double value, String unit) {
            this.id = id;
            this.name = name;
            this.value = value;
            this.unit = unit;
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

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
