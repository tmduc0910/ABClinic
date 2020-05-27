package com.abclinic.dto;

public class InquiryDto {
    private int type;
    private String albumId;
    private String content;
    private String date;
    private String time;

    public InquiryDto(int type, String albumId, String content, String date, String time) {
        this.type = type;
        this.albumId = albumId;
        this.content = content;
        this.date = date;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
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
