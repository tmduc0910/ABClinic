package com.abclinic.utils.services.intent.job;

import androidx.core.app.JobIntentService;

import com.abclinic.room.utils.AppDatabase;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CustomJobIntentService extends JobIntentService {
    public static final int RESULT_NOTI = 1;
    public static final int RESULT_INQUIRY = 2;
    public static final int RESULT_SCHEDULE = 3;

    protected static AtomicInteger autoId = new AtomicInteger(33000);
    private static Map<String, Integer> map = new LinkedHashMap<>();

    protected AppDatabase appDatabase;

    protected synchronized static int getJobId(Class c) {
        try {
            return map.get(c.getName());
        } catch (NullPointerException e) {
            int jobId = autoId.getAndIncrement();
            map.put(c.getName(), jobId);
            return jobId;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.appDatabase = AppDatabase.getInstance(this);
    }
}
