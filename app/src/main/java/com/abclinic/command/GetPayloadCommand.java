package com.abclinic.command;

public interface GetPayloadCommand<T> {
    T execute();
}
