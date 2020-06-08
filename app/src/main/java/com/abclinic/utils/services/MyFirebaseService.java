package com.abclinic.utils.services;

import android.util.Log;

import com.abclinic.dto.PushNotificationDto;
import com.abclinic.websocket.observer.Subject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseService";
    public static Subject<PushNotificationDto> subject = new Subject<PushNotificationDto>() {
    };

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // handle a notification payload.
        try {
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                String json = remoteMessage.getData().get("content");
                PushNotificationDto notificationDto = new ObjectMapper().readValue(json, PushNotificationDto.class);
                subject.notifyChange(notificationDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
