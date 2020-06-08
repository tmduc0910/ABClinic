package com.abclinic.dto;

public class TextValueDto {
    private long id;
    private String value;

    public TextValueDto(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
