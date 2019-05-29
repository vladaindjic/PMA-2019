package com.mindorks.framework.mvp.service.notification;

import android.annotation.SuppressLint;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mindorks.framework.mvp.MvpApp;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.Notification;
import com.mindorks.framework.mvp.di.component.DaggerServiceComponent;
import com.mindorks.framework.mvp.di.component.ServiceComponent;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class NotificationFirebaseService extends FirebaseMessagingService {


    private static final String TAG = "NotificationFirebaseService";

    @Inject
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceComponent component = DaggerServiceComponent.builder()
                .applicationComponent(((MvpApp) getApplication()).getComponent())
                .build();
        component.inject(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            System.out.println("Title " + remoteMessage.getNotification().getTitle());
            System.out.println("Body " + remoteMessage.getNotification().getBody());


            Notification notification = new Notification();
            notification.setNotificationTitle(remoteMessage.getNotification().getTitle());
            notification.setNotificationBody(remoteMessage.getNotification().getBody());
            notification.setPrmotionId(remoteMessage.getData().get("notificationPromotionId"));

            //Save
             dataManager.saveNotification(notification).subscribe(new Consumer<Boolean>() {
                 @Override
                 public void accept(Boolean aBoolean) throws Exception {
                     System.out.println("Notifikacija upisana");
                 }
             });
        }
    }


}
