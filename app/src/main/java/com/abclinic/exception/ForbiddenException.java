package com.abclinic.exception;

import com.abclinic.constant.HttpStatus;

public class ForbiddenException extends CustomRuntimeException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
