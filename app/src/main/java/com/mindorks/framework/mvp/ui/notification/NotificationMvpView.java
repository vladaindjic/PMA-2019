package com.mindorks.framework.mvp.ui.notification;

import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface NotificationMvpView extends MvpView {
    void updateNotifications(List<NotificationResponse.Notifications.Notification> notifications);
}
