package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindorks.framework.mvp.data.db.model.KitchenOption;

import java.util.ArrayList;
import java.util.List;

public class FilterRestaurantRequest {

    @Expose
    @SerializedName("query")
    private String query;

    @Expose
    @SerializedName("filter")
    private UserFilter userFilter;

    public FilterRestaurantRequest() {

    }

    public FilterRestaurantRequest(String query,
                                   com.mindorks.framework.mvp.data.db.model.UserFilter userFilter) {

        this.query = query;
        this.userFilter = null;

        if (userFilter != null) {
            this.userFilter = new UserFilter();
            this.userFilter.setId(userFilter.getId());
            this.userFilter.setDelivery(userFilter.isDelivery());
            this.userFilter.setDistance(userFilter.getDistance());
            this.userFilter.setDailyMenu(userFilter.isDailyMenu());
            this.userFilter.setOpen(userFilter.isOpen());
            List<UserFilter.KitchenOption> kitchenOptionList = new ArrayList<>();
            for(KitchenOption ko: userFilter.getKitchenOptionList()) {
                if (ko.getChecked()) {
                    // dodajemo samo checkirane kuhinje
                    kitchenOptionList.add(new UserFilter.KitchenOption(ko.getId(), ko.getKitchenName()));
                }
            }
        }

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public UserFilter getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(UserFilter userFilter) {
        this.userFilter = userFilter;
    }

    public static class UserFilter {
        @Expose
        @SerializedName("id")
        private Long id;

        @Expose
        @SerializedName("delivery")
        private boolean delivery;

        @Expose
        @SerializedName("open")
        private boolean open;

        @Expose
        @SerializedName("daily_menu")
        private boolean dailyMenu;

        @Expose
        @SerializedName("distance")
        private double distance;

        @Expose
        @SerializedName("kitchens")
        private List<KitchenOption> kitchenOptionList;

        public UserFilter(){}

        public UserFilter(Long id, boolean delivery, boolean open, boolean dailyMenu, double distance, List<KitchenOption> kitchenOptionList) {
            this.id = id;
            this.delivery = delivery;
            this.open = open;
            this.dailyMenu = dailyMenu;
            this.distance = distance;
            this.kitchenOptionList = kitchenOptionList;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public boolean isDelivery() {
            return delivery;
        }

        public void setDelivery(boolean delivery) {
            this.delivery = delivery;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public boolean isDailyMenu() {
            return dailyMenu;
        }

        public void setDailyMenu(boolean dailyMenu) {
            this.dailyMenu = dailyMenu;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public List<KitchenOption> getKitchenOptionList() {
            return kitchenOptionList;
        }

        public void setKitchenOptionList(List<KitchenOption> kitchenOptionList) {
            this.kitchenOptionList = kitchenOptionList;
        }

        public static class KitchenOption {
            @Expose
            @SerializedName("id")
            private Long id;
            @Expose
            @SerializedName("name")
            private String name;

            public KitchenOption() {
            }

            public KitchenOption(Long id, String name) {
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
    }


}
