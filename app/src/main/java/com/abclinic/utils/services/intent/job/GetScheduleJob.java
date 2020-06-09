package com.abclinic.utils.services.intent.job;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;

import com.abclinic.dto.ScheduleListDto;
import com.abclinic.entity.HealthIndexSchedule;
import com.abclinic.entity.PageableEntity;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.HealthIndexMapper;
import com.abclinic.utils.services.LocalStorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GetScheduleJob extends CustomJobIntentService {
    public static final int size = 20;
    public static int page = 1;
    public static boolean isLast = false;
    private static int jobId = 0;

    public GetScheduleJob() {
    }

    public static void enqueueWork(Context context, ServiceResultReceiver receiver) {
        if (jobId == 0)
            jobId = getJobId(GetScheduleJob.class);
        Intent intent = new Intent(context, GetScheduleJob.class);
        intent.putExtra("receiver", receiver);
        enqueueWork(context, GetScheduleJob.class, jobId, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String uid = new LocalStorageService(this, null).getUid();
        Retrofit retrofit = RetrofitClient.getClient(uid);
        Call<PageableEntity<HealthIndexSchedule>> call = retrofit.create(HealthIndexMapper.class)
                .getSchedules(page, size);
        try {
            PageableEntity<HealthIndexSchedule> schedules = call.execute().body();
            isLast = schedules.isLast();
            ArrayList<HealthIndexSchedule> list = (ArrayList<HealthIndexSchedule>) schedules.getContent()
                    .stream()
                    .map(s -> {
                        Call<HealthIndexSchedule> detailCall = retrofit.create(HealthIndexMapper.class)
                                .getSchedule(s.getId());
                        try {
                            s = detailCall.execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return s;
                    })
                    .collect(Collectors.toList());

            if (receiver != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("schedules", list);
                receiver.send(RESULT_SCHEDULE, bundle);
            }
            SaveScheduleJob.enqueueWork(GetScheduleJob.this, new ScheduleListDto(list));
            if (!isLast)
                page++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
