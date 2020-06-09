package com.abclinic.utils.services.intent.job;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;

import com.abclinic.dto.NotificationListDto;
import com.abclinic.entity.Notification;
import com.abclinic.entity.PageableEntity;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.NotificationMapper;
import com.abclinic.utils.services.LocalStorageService;

import java.io.IOException;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GetNotificationJob extends CustomJobIntentService {
    public static final int size = 20;
    public static int page = 1;
    public static boolean isLast = false;
    private static int jobId = 0;

    public GetNotificationJob() {
    }

    public static void enqueueWork(Context context, ServiceResultReceiver receiver) {
        if (jobId == 0)
            jobId = getJobId(GetNotificationJob.class);
        Intent intent = new Intent(context, GetNotificationJob.class);
        intent.putExtra("receiver", receiver);
        enqueueWork(context, GetNotificationJob.class, jobId, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        long userId = appDatabase.getUserDao().getLogonUser().getUserId();
        String uid = new LocalStorageService(this, null).getUid();
        Retrofit retrofit = RetrofitClient.getClient(uid);
        Call<PageableEntity<Notification>> call = retrofit.create(NotificationMapper.class).getNotificationList(page, size);
        try {
            PageableEntity<Notification> notifications = call.execute().body();
            isLast = notifications.isLast();
            NotificationListDto list = new NotificationListDto();
            list.addItems(false, notifications.getContent());

            Bundle bundle = new Bundle();
            bundle.putSerializable("notifications", list);
            receiver.send(RESULT_NOTI, bundle);
            SaveDataJob.enqueueWork(GetNotificationJob.this, new NotificationListDto(new LinkedList<>(notifications.getContent())));
            page++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
