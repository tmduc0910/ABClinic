package com.abclinic.utils.services.intent;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.abclinic.entity.ISaveable;
import com.abclinic.entity.Notification;
import com.abclinic.room.entity.DataEntity;
import com.abclinic.room.utils.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class SaveDataService extends IntentService {
    public static final String ROOT = "com.abclinic.utils.service.intent.IntentService";

    private long userId;
    private AppDatabase appDatabase;
    private ISaveable[] saveables;

    public SaveDataService(long userId, AppDatabase appDatabase, List<Notification> notifications) {
        super("SaveDataService");
        this.userId = userId;
        this.appDatabase = appDatabase;
        this.saveables = notifications.stream()
                .map(Notification::getData)
                .toArray(ISaveable[]::new);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int size = appDatabase.getDataDao().getSize();
        List<DataEntity> list = new ArrayList<>();

        if (size == 0) {
            for (ISaveable data : saveables) {
                list.add(new DataEntity(userId, data.getDataType(), data.getId(), data.getCreatedAt()));
            }
        } else {
            DataEntity latest = appDatabase.getDataDao().getLatestData();
            for (ISaveable data : saveables) {
                if ((data.getId() == latest.getPayloadId() && data.getDataType() == latest.getType()))
                    break;
                list.add(new DataEntity(userId, data.getDataType(), data.getId(), data.getCreatedAt()));
            }
        }
        appDatabase.getDataDao().save(list.toArray(new DataEntity[0]));
    }
}
