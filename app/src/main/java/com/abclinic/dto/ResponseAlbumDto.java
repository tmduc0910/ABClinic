package com.abclinic.dto;

import java.util.List;

public class ResponseAlbumDto {
    private String albumId;
    private List<String> images;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
