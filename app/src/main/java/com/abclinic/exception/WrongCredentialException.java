package com.abclinic.exception;

import com.abclinic.constant.HttpStatus;

public class WrongCredentialException extends CustomRuntimeException {
    public WrongCredentialException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
