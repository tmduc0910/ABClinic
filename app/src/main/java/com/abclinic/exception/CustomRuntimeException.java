package com.abclinic.exception;

public abstract class CustomRuntimeException extends RuntimeException {
    private int code;
    private String message;

    public CustomRuntimeException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
