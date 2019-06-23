package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DishRequestDto {
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
    private KitchenRequestDto kitchen;

    @Expose
    @SerializedName("nutritive_values")
    private List<NutritiveValueRequestDto> nutritiveValues;


    public DishRequestDto(DishDetailsResponse.DishDetails dish) {
        this.name = dish.getName();
        this.description = dish.getDescription();
        this.price = dish.getPrice();
        this.kitchen =  new KitchenRequestDto(dish.getKitchen().getName());
        this.nutritiveValues = new ArrayList<>();
        for(DishDetailsResponse.NutritiveValue nv:dish.getNutritiveValues()){
            NutritiveValueRequestDto value = new NutritiveValueRequestDto(nv.getName(),nv.getValue(),nv.getUnit());
            this.nutritiveValues.add(value);
        }

    }

    public DishRequestDto(String name, String description, Double price, KitchenRequestDto kitchen, List<NutritiveValueRequestDto> nutritiveValues) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.kitchen = kitchen;
        this.nutritiveValues = nutritiveValues;
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

    public KitchenRequestDto getKitchen() {
        return kitchen;
    }

    public void setKitchen(KitchenRequestDto kitchen) {
        this.kitchen = kitchen;
    }

    public List<NutritiveValueRequestDto> getNutritiveValues() {
        return nutritiveValues;
    }

    public void setNutritiveValues(List<NutritiveValueRequestDto> nutritiveValues) {
        this.nutritiveValues = nutritiveValues;
    }
}
