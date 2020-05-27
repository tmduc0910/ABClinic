package com.abclinic.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtils {
    public static File createFile(Context context) throws IOException {
        String timeStamp = DateTimeUtils.toUrlString(LocalDateTime.now());
        String imgFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imgFileName,
                ".jpg",
                storageDir
        );
    }

    public static MultipartBody.Part getPart(String uri) {
        File file = new File(uri);
        return getPart(file);
    }

    public static MultipartBody.Part getPart(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        return MultipartBody.Part.createFormData("files", file.getName(), requestBody);
    }

    public static String getAbsolutePath(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }
}
