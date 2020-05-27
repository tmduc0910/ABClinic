package com.abclinic.dto;

import android.graphics.Bitmap;
import android.net.Uri;

public class RecyclerImageDto {
    private Uri uri;
    private Bitmap bitmap;
    private int resourceId;
    private String path;

    public RecyclerImageDto() {
    }

    public RecyclerImageDto(Uri uri) {
        this.uri = uri;
    }

    public RecyclerImageDto(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public RecyclerImageDto(int resourceId) {
        this.resourceId = resourceId;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = Uri.parse(uri);
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
