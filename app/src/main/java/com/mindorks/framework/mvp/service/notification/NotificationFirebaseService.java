package com.mindorks.framework.mvp.service.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mindorks.framework.mvp.MvpApp;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.DataManager;
import com.mindorks.framework.mvp.data.db.model.Notification;
import com.mindorks.framework.mvp.di.component.DaggerServiceComponent;
import com.mindorks.framework.mvp.di.component.ServiceComponent;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.details.PromotionDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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


        super.onMessageReceived(remoteMessage);
        System.out.println("Notifikacija");
        System.out.println("Title " + remoteMessage.getData().get("title"));
        System.out.println("Body " + remoteMessage.getData().get("body"));
        System.out.println("Id " + remoteMessage.getData().get("notificationPromotionId"));

        Intent intent = new Intent(this, PromotionDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notificationPromotionId", remoteMessage.getData().get("notificationPromotionId"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this,createId(),intent,0);

        String channelId = "pma_channel";

        //Create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "PMA Channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(createId(), builder.build());


        Notification notification = new Notification();
        notification.setNotificationTitle(remoteMessage.getData().get("title"));
        notification.setNotificationBody(remoteMessage.getData().get("body"));
        notification.setPrmotionId(remoteMessage.getData().get("notificationPromotionId"));

        //Save
        dataManager.saveNotification(notification).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                System.out.println("Notifikacija upisana");
            }
        });
    }


    private int createId() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
        return id;
    }


}
