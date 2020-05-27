package com.abclinic.exception;

import com.abclinic.constant.HttpStatus;

public class NotFoundException extends CustomRuntimeException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
