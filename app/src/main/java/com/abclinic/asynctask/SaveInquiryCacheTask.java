package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.constant.StorageConstant;
import com.abclinic.dto.InquiryCacheDto;
import com.abclinic.utils.DateTimeUtils;

import java.util.HashSet;

public class SaveInquiryCacheTask extends CustomAsyncTask<InquiryCacheDto, Void, Void> {
    public SaveInquiryCacheTask(Context context, String storageKey) {
        super(context, storageKey, null);
    }

    @Override
    protected Void doInBackground(InquiryCacheDto... inquiryCacheDtos) {
        InquiryCacheDto dto = inquiryCacheDtos[0];
        storageService.saveCache(StorageConstant.KEY_TYPE, dto.getType());
        storageService.saveCache(StorageConstant.KEY_URI, new HashSet<>(dto.getUris()));
        storageService.saveCache(StorageConstant.KEY_CONTENT, dto.getContent());
        storageService.saveCache(StorageConstant.KEY_DATE, DateTimeUtils.toString(dto.getDate()));
        storageService.saveCache(StorageConstant.KEY_TIME, dto.getTime());
        return null;
    }
}
