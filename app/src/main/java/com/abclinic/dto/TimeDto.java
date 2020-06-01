package com.abclinic.dto;

public class TimeDto {
    private final long value;
    private final String timeUnit;

    public TimeDto(long value, String timeUnit) {
        this.value = value;
        this.timeUnit = timeUnit;
    }

    public long getValue() {
        return value;
    }

    public String getTimeUnit() {
        return timeUnit;
    }
}
