package com.abclinic.asynctask;

import android.content.Context;

import com.abclinic.entity.ISaveable;
import com.abclinic.room.entity.DataEntity;
import com.abclinic.room.utils.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class SaveDataTask extends CustomAsyncTask<ISaveable, Void, List<DataEntity>> {
    public static int size = 0;
    private long userId;
    private AppDatabase appDatabase;

    public SaveDataTask(Context context, long userId, AppDatabase appDatabase) {
        super(context, null, null);
        this.userId = userId;
        this.appDatabase = appDatabase;
    }

    @Override
    protected List<DataEntity> doInBackground(ISaveable... iSaveables) {
        size = appDatabase.getDataDao().getSize();
        List<DataEntity> list = new ArrayList<>();

        if (size == 0) {
            for (ISaveable data : iSaveables) {
                list.add(new DataEntity(userId, data.getDataType(), data.getId(), data.getCreatedAt()));
            }
        } else {
            DataEntity latest = appDatabase.getDataDao().getLatestData();
            for (ISaveable data : iSaveables) {
                if ((data.getId() == latest.getPayloadId() && data.getDataType() == latest.getType()))
                    break;
                list.add(new DataEntity(userId, data.getDataType(), data.getId(), data.getCreatedAt()));
            }
        }
        appDatabase.getDataDao().save(list.toArray(new DataEntity[0]));
        return list;
    }
}
