package com.mindorks.framework.mvp.service.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mindorks.framework.mvp.R;

public class NotificationFirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            System.out.println("Title " + remoteMessage.getNotification().getTitle());
            System.out.println("Body " + remoteMessage.getNotification().getBody());




        }
    }



}
