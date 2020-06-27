package com.abclinic.utils.services.intent.job;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.abclinic.constant.NotificationType;
import com.abclinic.entity.Inquiry;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.InquiryMapper;
import com.abclinic.room.entity.DataEntity;
import com.abclinic.utils.services.LocalStorageService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GetInquiryJob extends CustomJobIntentService {
    private static int jobId = 0;

    public static void enqueueWork(Context context, int month, int year) {
        if (jobId == 0)
            jobId = getJobId(GetInquiryJob.class);
        Intent intent = new Intent(context, GetInquiryJob.class);
        intent.putExtra("month", month);
        intent.putExtra("year", year);
        enqueueWork(context, GetInquiryJob.class, jobId, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        int month = intent.getExtras().getInt("month");
        int year = intent.getExtras().getInt("year");
        Retrofit retrofit = RetrofitClient.getClient(new LocalStorageService(this, null).getUid());
        Call<List<Inquiry>> call = retrofit.create(InquiryMapper.class).getInquiryListByMonth(month, year);
        try {
            ArrayList<Inquiry> results = (ArrayList<Inquiry>) call.execute().body();
            List<DataEntity> latestList = appDatabase.getDataDao().getAllByType(NotificationType.INQUIRY.getValue());
            if (results != null) {
                results.removeAll(latestList);
                SaveDataJob.enqueueWork(this, results);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
