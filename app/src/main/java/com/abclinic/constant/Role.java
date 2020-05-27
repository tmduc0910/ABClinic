package com.abclinic.constant;

public enum Role {
    PRACTITIONER(0, "PRACTITIONER"),
    SPECIALIST(1, "SPECIALIST"),
    DIETITIAN(2, "DIETITIAN"),
    PATIENT(3, "PATIENT"),
    COORDINATOR(4, "COORDINATOR");

    private int value;
    private String description;

    Role(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
