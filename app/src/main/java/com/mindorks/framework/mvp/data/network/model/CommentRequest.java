package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentRequest {

    @Expose
    @SerializedName("text")
    private String text;

    public CommentRequest(String text) {
        this.text = text;
    }

    public CommentRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
