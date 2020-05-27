package com.abclinic.utils.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import com.abclinic.constant.StorageConstant;
import com.abclinic.dto.InquiryCacheDto;
import com.abclinic.entity.UserInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Set;

public class LocalStorageService {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public LocalStorageService(Context context, String key) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public String getUid() {
        sharedPreferences = context.getSharedPreferences(StorageConstant.STORAGE_KEY_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString(StorageConstant.KEY_UID, null);
    }

    public InquiryCacheDto getInquiryCache() {
        sharedPreferences = context.getSharedPreferences(StorageConstant.STORAGE_KEY_INQUIRY, Context.MODE_PRIVATE);
        InquiryCacheDto dto = new InquiryCacheDto();
        dto.setType(sharedPreferences.getInt(StorageConstant.KEY_TYPE, -1));
        dto.setUris(new LinkedList<>(sharedPreferences.getStringSet(StorageConstant.KEY_URI, new ArraySet<>())));
        dto.setContent(sharedPreferences.getString(StorageConstant.KEY_CONTENT, null));
        dto.setDate(sharedPreferences.getString(StorageConstant.KEY_DATE, null));
        dto.setTime(sharedPreferences.getString(StorageConstant.KEY_TIME, null));
        return dto;
    }

    public void deleteInquiryCache() {
        sharedPreferences = context.getSharedPreferences(StorageConstant.STORAGE_KEY_INQUIRY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public UserInfo getUserInfo() {
        sharedPreferences = context.getSharedPreferences(StorageConstant.STORAGE_KEY_USER, Context.MODE_PRIVATE);
        return JsonService.toObject(sharedPreferences.getString(StorageConstant.KEY_USER, ""), UserInfo.class);
    }

    public void saveCache(String tag, int value) {
        editor = sharedPreferences.edit();
        editor.putInt(tag, value);
        editor.apply();
    }

    public void saveCache(String tag, String value) {
        editor = sharedPreferences.edit();
        editor.putString(tag, value);
        editor.apply();
    }

    public void saveCache(String tag, Set<String> value) {
        editor = sharedPreferences.edit();
        editor.putStringSet(tag, value);
        editor.apply();
    }

    public void saveCache(String tag, boolean value) {
        editor = sharedPreferences.edit();
        editor.putBoolean(tag, false);
        editor.apply();
    }

    public void write(FileOutputStream fos, Object o) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        writer.write(o.toString());
        writer.close();
    }

    public String read(FileInputStream fis, int blockSize) throws IOException {
        InputStreamReader reader = new InputStreamReader(fis);
        char[] buffer = new char[blockSize];
        StringBuilder builder = new StringBuilder();
        int charRead;

        while ((charRead = reader.read(buffer)) > 0) {
            builder.append(String.copyValueOf(buffer, 0, charRead));
        }
        reader.close();
        return builder.toString();
    }
}
