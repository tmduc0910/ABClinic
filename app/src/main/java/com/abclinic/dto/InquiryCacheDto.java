package com.abclinic.dto;

import com.abclinic.utils.DateTimeUtils;

import java.time.LocalDate;
import java.util.List;

public class InquiryCacheDto {
    private List<String> uris;
    private int type;
    private String content;
    private String date;
    private String time;

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        if (date == null)
            return null;
        return DateTimeUtils.parseDate(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
