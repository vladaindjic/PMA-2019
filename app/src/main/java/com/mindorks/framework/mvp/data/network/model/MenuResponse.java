package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MenuResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private Menu data;

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

    public Menu getData() {
        return data;
    }

    public void setData(Menu data) {
        this.data = data;
    }


    public static class Menu {
        @Expose
        @SerializedName("id")
        private Long id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("dish_types")
        private List<DishType> dishTypeList;

        public Menu() {
        }

        public Menu(Long id, String name, List<DishType> dishTypeList) {
            this.id = id;
            this.name = name;
            this.dishTypeList = dishTypeList;
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

        public List<DishType> getDishTypeList() {
            return dishTypeList;
        }

        public void setDishTypeList(List<DishType> dishTypeList) {
            this.dishTypeList = dishTypeList;
        }
    }

    public static class DishType {
        @Expose
        @SerializedName("id")
        private Long id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("dishes")
        private List<Dish> dishList;

        public DishType() {
        }

        public DishType(Long id, String name, List<Dish> dishList) {
            this.id = id;
            this.name = name;
            this.dishList = dishList;
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

        public List<Dish> getDishList() {
            return dishList;
        }

        public void setDishList(List<Dish> dishList) {
            this.dishList = dishList;
        }

        // kopiramo sve, ali ostavljamo iste objekte klase Dish
        public DishType copyAllButDishes() {
            DishType copy = new DishType();
            copy.setId(this.id);
            copy.setName(this.name);
            copy.setDishList(new ArrayList<Dish>());
            copy.getDishList().addAll(this.dishList);
            return copy;
        }
    }

    public static class Dish {
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

        // TODO vi3: vidi da li ovde ima smisla nesto dodavati ili to samo za glavni prikaz

        public Dish() {
        }

        public Dish(Long id, String name, String imgUrl, Double price) {
            this.id = id;
            this.name = name;
            this.imgUrl = imgUrl;
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
    }
}
