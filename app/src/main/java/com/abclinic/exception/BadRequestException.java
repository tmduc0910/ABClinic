package com.abclinic.exception;

import com.abclinic.constant.HttpStatus;

public class BadRequestException extends CustomRuntimeException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
