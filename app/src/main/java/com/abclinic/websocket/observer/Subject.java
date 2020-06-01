package com.abclinic.websocket.observer;

import com.abclinic.dto.PushNotificationDto;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private PushNotificationDto data = null;
    private List<IObserver> observers = new ArrayList<>();

    public void attach(IObserver observer) {
        observers.add(observer);
    }

    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    public void detachAll() {
        observers.clear();
    }

    public void notifyChange(PushNotificationDto data) {
        this.data = data;
        for (IObserver o : observers) {
            o.process(data);
        }
    }

    public PushNotificationDto getData() {
        return data;
    }
}
