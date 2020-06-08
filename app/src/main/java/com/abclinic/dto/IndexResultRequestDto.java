package com.abclinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndexResultRequestDto {
    @JsonProperty("field_id")
    private long fieldId;
    private String value;

    public IndexResultRequestDto(long fieldId, String value) {
        this.fieldId = fieldId;
        this.value = value;
    }

    public long getFieldId() {
        return fieldId;
    }

    public String getValue() {
        return value;
    }
}
