package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantScoreRequest {

    @Expose
    @SerializedName("score")
    private Integer score;

    public RestaurantScoreRequest(Integer score) {
        this.score = score;
    }

    public RestaurantScoreRequest() {
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
