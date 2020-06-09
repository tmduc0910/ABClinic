package com.abclinic.utils.services.intent.job;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.abclinic.dto.NotificationListDto;
import com.abclinic.entity.ISaveable;
import com.abclinic.entity.Notification;
import com.abclinic.entity.SaveableImpl;
import com.abclinic.room.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SaveDataJob extends CustomJobIntentService {
    private static int jobId = 0;

    public SaveDataJob() {
    }

    public static void enqueueWork(Context context, NotificationListDto notifications) {
        ArrayList<ISaveable> datas = (ArrayList<ISaveable>) notifications.getList().stream()
                .map(Notification::getData)
                .sorted((o1, o2) -> o1.getCreatedAt().compareTo(o2.getCreatedAt()))
                .collect(Collectors.toList());
        enqueueWork(context, datas);
    }

    protected static void enqueueWork(Context context, ArrayList<? extends ISaveable> datas) {
        if (jobId == 0)
            jobId = getJobId(SaveDataJob.class);
        Intent intent = new Intent(context, SaveDataJob.class);
        intent.putParcelableArrayListExtra("data", datas);
        enqueueWork(context, SaveDataJob.class, jobId, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        long userId = appDatabase.getUserDao().getLogonUser().getUserId();
        ISaveable[] saveables = intent.getExtras().getParcelableArrayList("data")
                .toArray(new ISaveable[0]);

        int size = appDatabase.getDataDao().getSize();
        List<DataEntity> list = new ArrayList<>();

        if (size == 0) {
            for (ISaveable data : saveables) {
                list.add(new DataEntity(userId, data.getDataType(), data.getId(), data.getCreatedAt()));
            }
        } else {
            for (ISaveable data : saveables) {
                if (data instanceof SaveableImpl) {
                    if (appDatabase.getDataDao().findByNotificationId((data.getNotificationId())) != null)
                        break;
                    list.add(new DataEntity(userId, data.getDataType(), data.getNotificationId(), data.getId(), data.getCreatedAt()));
                } else
                    list.add(new DataEntity(userId, data.getDataType(), data.getId(), data.getCreatedAt()));
            }
        }
        appDatabase.getDataDao().save(list.toArray(new DataEntity[0]));
    }
}
