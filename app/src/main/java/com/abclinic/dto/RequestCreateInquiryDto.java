package com.abclinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestCreateInquiryDto {
    private int type;
    @JsonProperty("album_id")
    private String albumId;
    private String content;
    @JsonProperty("chain_id")
    private long chainId;
    private String date;

    public RequestCreateInquiryDto(int type, String albumId, long chainId, String content, String date) {
        this.type = type;
        this.albumId = albumId;
        this.content = content;
        this.date = date;
        this.chainId = chainId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonProperty("album_id")
    public String getAlbumId() {
        return albumId;
    }

    @JsonProperty("album_id")
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public long getChainId() {
        return chainId;
    }

    public void setChainId(long chainId) {
        this.chainId = chainId;
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
}
