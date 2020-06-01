package com.abclinic.websocket.observer;

import com.abclinic.dto.PushNotificationDto;

public interface IObserver {
    void process(PushNotificationDto notificationDto);
}
