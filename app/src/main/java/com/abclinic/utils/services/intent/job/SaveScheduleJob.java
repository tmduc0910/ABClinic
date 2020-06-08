package com.abclinic.utils.services.intent.job;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.abclinic.dto.ScheduleListDto;
import com.abclinic.entity.HealthIndexSchedule;
import com.abclinic.room.entity.ScheduleEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveScheduleJob extends CustomJobIntentService {
    private static int jobId = 0;

    public static void enqueueWork(Context context, ScheduleListDto schedules) {
        if (jobId == 0)
            jobId = getJobId(SaveDataJob.class);
        Intent intent = new Intent(context, SaveDataJob.class);
        intent.putParcelableArrayListExtra("data", new ArrayList<>(schedules.getList()));
        enqueueWork(context, SaveScheduleJob.class, jobId, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        long userId = appDatabase.getUserDao().getLogonUser().getUserId();
        HealthIndexSchedule[] saveables = intent.getExtras().getParcelableArrayList("data")
                .toArray(new HealthIndexSchedule[0]);

        int size = appDatabase.getDataDao().getSize();
        ScheduleEntity[] scheduleEntities;

        if (size == 0) {
            scheduleEntities = Arrays.stream(saveables)
                    .map(data -> new ScheduleEntity(userId, data))
                    .toArray(ScheduleEntity[]::new);
        } else {
            scheduleEntities = Arrays.stream(saveables)
                    .map(data -> {
                        ScheduleEntity temp = appDatabase.getScheduleDao().getByScheduleId(data.getId());
                        if (temp != null) {
                            temp.setStatus(data.getStatus());
                            temp.setStartedAt(data.getStartedAt());
                            temp.setEndedAt(data.getEndedAt());
                        } else {
                            temp = new ScheduleEntity(userId, data);
                        }
                        return temp;
                    })
                    .toArray(ScheduleEntity[]::new);
        }
        appDatabase.getScheduleDao().save(scheduleEntities);
    }
}
