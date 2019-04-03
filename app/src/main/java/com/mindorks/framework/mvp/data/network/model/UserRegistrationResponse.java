package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class UserRegistrationResponse {

    @Expose
    @SerializedName("http_response_code")
    private int httpResponseCode;
    @Expose
    @SerializedName("successful")
    private boolean succesful;
    @Expose
    @SerializedName("message")
    private String message;


    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public boolean isSuccesful() {
        return succesful;
    }

    public void setSuccesful(boolean succesful) {
        this.succesful = succesful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
