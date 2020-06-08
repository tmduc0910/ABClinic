package com.abclinic.websocket.observer;

public interface ISubject<T> {
    void attach(IObserver<T> observer);

    void detach(IObserver<T> observer);

    void notifyChange(T data);

    T getData();
}
