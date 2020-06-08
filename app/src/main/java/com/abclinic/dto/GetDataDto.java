package com.abclinic.dto;

public class GetDataDto {
    private long userId;
    private int month;
    private int year;

    public GetDataDto(long userId, int month, int year) {
        this.userId = userId;
        this.month = month;
        this.year = year;
    }

    public long getUserId() {
        return userId;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
