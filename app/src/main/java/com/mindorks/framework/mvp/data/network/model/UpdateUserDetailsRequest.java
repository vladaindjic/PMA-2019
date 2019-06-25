package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateUserDetailsRequest {

    @Expose
    @SerializedName("firstname")
    private String firstname;

    @Expose
    @SerializedName("lastname")
    private String lastname;

    @Expose
    @SerializedName("email")
    private String email;

    public UpdateUserDetailsRequest() {}

    public UpdateUserDetailsRequest(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
