package com.abclinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestCreateResultDto {
    @JsonProperty("schedule_id")
    private long scheduleId;
    private List<IndexResultRequestDto> results;

    public RequestCreateResultDto(long scheduleId, List<IndexResultRequestDto> results) {
        this.scheduleId = scheduleId;
        this.results = results;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public List<IndexResultRequestDto> getResults() {
        return results;
    }
}
