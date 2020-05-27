package com.abclinic.constant;

import com.example.abclinic.R;

import java.util.Arrays;

public enum PayloadStatus {
    PROCESSING(1, "đang xử lý", R.color.flat_red_1),
    PROCESSED(2, "đã được xử lý", R.color.flat_awesome_green_2);

    private int code;
    private String message;
    private int colorId;

    PayloadStatus(int code, String message, int colorId) {
        this.code = code;
        this.message = message;
        this.colorId = colorId;
    }

    public static PayloadStatus getStatus(int code) throws Throwable {
        return Arrays.stream(values()).filter(v -> v.getCode() == code).findFirst().orElseThrow(IllegalAccessException::new);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message.toUpperCase();
    }

    public int getColorId() {
        return colorId;
    }
}
