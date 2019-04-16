package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantFilterResponse {
    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private RestaurantFilterResponse.RestaurantFilter data;

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

    public RestaurantFilterResponse.RestaurantFilter getData() {
        return data;
    }

    public void setData(RestaurantFilterResponse.RestaurantFilter data) {
        this.data = data;
    }


    public static class RestaurantFilter {
        @Expose
        @SerializedName("id")
        private Long id;

        @Expose
        @SerializedName("distance")
        private int distance;
        @Expose
        @SerializedName("kitchenOptions")
        private List<KitchenOptions> kitchenOptions;

        @Expose
        @SerializedName("restaurantFilterOptions")
        private List<RestaurantFilterOptions> restaurantFilterOptions;

        public RestaurantFilter(Long id) {
            this.id = id;
        }

        public RestaurantFilter(Long id, int distance, List<KitchenOptions> kitchenOptions, List<RestaurantFilterOptions> restaurantFilterOptions) {
            this.id = id;
            this.distance = distance;
            this.kitchenOptions = kitchenOptions;
            this.restaurantFilterOptions = restaurantFilterOptions;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public List<KitchenOptions> getKitchenOptions() {
            return kitchenOptions;
        }

        public void setKitchenOptions(List<KitchenOptions> kitchenOptions) {
            this.kitchenOptions = kitchenOptions;
        }

        public List<RestaurantFilterOptions> getRestaurantFilterOptions() {
            return restaurantFilterOptions;
        }

        public void setRestaurantFilterOptions(List<RestaurantFilterOptions> restaurantFilterOptions) {
            this.restaurantFilterOptions = restaurantFilterOptions;
        }

        public static class KitchenOptions {
            @Expose
            @SerializedName("id")
            private Long id;
            @Expose
            @SerializedName("name")
            private String name;
            @Expose
            @SerializedName("value")
            private Boolean value;

            public KitchenOptions(Long id, String name, Boolean value) {
                this.id = id;
                this.name = name;
                this.value = value;
            }

            public KitchenOptions(String name) {
                this.name = name;
            }

            public KitchenOptions(String name, Boolean value) {
                this.name = name;
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Boolean getValue() {
                return value;
            }

            public void setValue(Boolean value) {
                this.value = value;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }
        }

        public static class RestaurantFilterOptions {
            @Expose
            @SerializedName("id")
            private Long id;

            @Expose
            @SerializedName("name")
            private String name;

            @Expose
            @SerializedName("value")
            private Boolean value;

            public RestaurantFilterOptions(Long id, String name, Boolean value) {
                this.id = id;
                this.name = name;
                this.value = value;
            }

            public RestaurantFilterOptions() {
            }

            public RestaurantFilterOptions(String name, Boolean value) {
                this.name = name;
                this.value = value;
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

            public Boolean getValue() {
                return value;
            }

            public void setValue(Boolean value) {
                this.value = value;
            }
        }
    }
}
