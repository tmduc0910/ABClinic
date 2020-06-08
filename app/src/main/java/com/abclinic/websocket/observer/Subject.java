package com.abclinic.websocket.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject<T> implements ISubject<T> {
    private T data = null;
    private List<IObserver<T>> observers = new ArrayList<>();

    public void attach(IObserver<T> observer) {
        observers.add(observer);
    }

    public void detach(IObserver<T> observer) {
        observers.remove(observer);
    }

    public void detachAll() {
        observers.clear();
    }

    public void notifyChange(T data) {
        this.data = data;
        for (IObserver<T> o : observers) {
            o.process(data);
        }
    }

    public T getData() {
        return data;
    }
}
