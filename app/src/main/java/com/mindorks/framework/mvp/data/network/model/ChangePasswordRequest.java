package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @Expose
    @SerializedName("old_password")
    private String oldPassword;

    @Expose
    @SerializedName("new_password")
    private String newPassword;

    @Expose
    @SerializedName("repeted_password")
    private String repetedPassword;

    public ChangePasswordRequest(){}

    public ChangePasswordRequest(String oldPassword, String newPassword, String repetedPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repetedPassword = repetedPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepetedPassword() {
        return repetedPassword;
    }

    public void setRepetedPassword(String repetedPassword) {
        this.repetedPassword = repetedPassword;
    }
}
