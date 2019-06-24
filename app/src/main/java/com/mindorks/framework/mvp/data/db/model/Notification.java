package com.mindorks.framework.mvp.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "notification")
public class Notification {

    @Expose
    @SerializedName("id")
    @Id(autoincrement = true)
    private Long id;

    @Expose
    @SerializedName("notification_title")
    @Property(nameInDb = "notification_title")
    private String notificationTitle;

    @Expose
    @SerializedName("notification_body")
    @Property(nameInDb = "notification_body")
    private String notificationBody;

    @Expose
    @SerializedName("promotion_id")
    @Property(nameInDb = "promotion_id")
    private String prmotionId;


    @Generated(hash = 1289331876)
    public Notification(Long id, String notificationTitle, String notificationBody,
            String prmotionId) {
        this.id = id;
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.prmotionId = prmotionId;
    }

    @Generated(hash = 1855225820)
    public Notification() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public String getPrmotionId() {
        return prmotionId;
    }

    public void setPrmotionId(String prmotionId) {
        this.prmotionId = prmotionId;
    }
}
