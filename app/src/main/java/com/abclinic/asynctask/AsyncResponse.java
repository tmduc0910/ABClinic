package com.abclinic.asynctask;

import com.abclinic.exception.CustomRuntimeException;

public interface AsyncResponse<T> {
    void processResponse(T res);

    void handleException(CustomRuntimeException e);
}
