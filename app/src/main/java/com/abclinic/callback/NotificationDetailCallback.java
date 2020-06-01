package com.abclinic.callback;

import android.content.Context;

import com.abclinic.entity.Notification;
import com.abclinic.retrofit.api.NotificationMapper;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationDetailCallback<T> extends CustomCallback<T> {
    private long notificationId;
    private Notification notification;

    public NotificationDetailCallback(Context context, String key, long notificationId) {
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
