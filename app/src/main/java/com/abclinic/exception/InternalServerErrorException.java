package com.abclinic.exception;

import com.abclinic.constant.HttpStatus;

public class InternalServerErrorException extends CustomRuntimeException {
    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER, message);
    }
}
