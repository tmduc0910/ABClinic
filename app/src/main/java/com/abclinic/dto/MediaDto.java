package com.abclinic.dto;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class MediaDto {
    private Intent intent;
    private File file;
    private Uri uri;

    public MediaDto() {
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
