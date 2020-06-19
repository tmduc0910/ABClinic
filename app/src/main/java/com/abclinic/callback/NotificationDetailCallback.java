package com.abclinic.callback;

import com.abclinic.entity.Notification;
import com.abclinic.retrofit.api.NotificationMapper;
import com.example.abclinic.activity.CustomActivity;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationDetailCallback<T> extends CustomCallback<T> {
    private long notificationId;
    private Notification notification;

    public NotificationDetailCallback(CustomActivity context, String key, long notificationId) {
        super(context);
        this.notificationId = notificationId;
    }

    public long getNotificationId() {
        return notificationId;
    }

    @Override
    protected void processResponse(Response<T> response) {
        Call<Notification> nCall = retrofit.create(NotificationMapper.class).getNotification(notificationId);

    }
}
