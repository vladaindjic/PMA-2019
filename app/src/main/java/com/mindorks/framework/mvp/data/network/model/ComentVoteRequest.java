package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComentVoteRequest {

    @Expose
    @SerializedName("voteEnum")
    private String voteEnum;

    public ComentVoteRequest() {
    }

    public ComentVoteRequest(String voteEnum) {
        this.voteEnum = voteEnum;
    }

    public String getVoteEnum() {
        return voteEnum;
    }

    public void setVoteEnum(String voteEnum) {
        this.voteEnum = voteEnum;
    }
}
