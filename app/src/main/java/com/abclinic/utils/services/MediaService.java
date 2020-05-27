package com.abclinic.utils.services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.abclinic.dto.MediaDto;
import com.abclinic.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class MediaService {
    public static MediaDto getCameraIntent(Context context) {
        MediaDto media = new MediaDto();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileUtils.createFile(context);
                media.setFile(photoFile);
            } catch (IOException ex) {
                Toast.makeText(context.getApplicationContext(), "Error while saving picture.", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.example.abclinic.fileprovider",
                        photoFile);
                media.setUri(photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                media.setIntent(takePictureIntent);
            }
        }
        return media;
    }

    public static Intent getGalleryMultipleIntent(String title) {
        Intent intent = getGalleryIntent();
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        return Intent.createChooser(intent, title);
    }

    public static Intent getGalleryIntent(String title) {
        return Intent.createChooser(getGalleryIntent(), title);
    }

    private static Intent getGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }
}
