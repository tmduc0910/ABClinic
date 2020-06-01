package com.abclinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushNotificationDto {
    @JsonProperty("notification_id")
    private long notificationId;
    @JsonProperty("user_id")
    private long userId;
    private String notification;
    private int type;

    public PushNotificationDto() {
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
