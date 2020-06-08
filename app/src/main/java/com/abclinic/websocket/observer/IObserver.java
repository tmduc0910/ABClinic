package com.abclinic.websocket.observer;

public interface IObserver<T> {
    void process(T obj);
}
