package com.example.abclinic.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.abclinic.constant.NotificationType;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.utils.services.LocalStorageService;
import com.abclinic.utils.services.PermissionUtils;

import retrofit2.Retrofit;

public abstract class CustomActivity extends AppCompatActivity {
    public static final String INQUIRY = "yêu cầu tư vấn";
    public static final String RECORD = "tư vấn";
    public static final String DISOWN = "hủy quyền tư vấn";
    public static final String SCHEDULE = "lịch gửi thông số";
    protected LocalStorageService storageService;
    protected Retrofit retrofit;
    protected PermissionUtils permissionUtils;
    private long pressBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageService = new LocalStorageService(this, getKey());
        retrofit = RetrofitClient.getClient(storageService.getUid());
    }

    @Override
    public void onBackPressed() {
        if (pressBack + 2000 > System.currentTimeMillis()) {
            moveTaskToBack(true);
            return;
        } else {
            Toast.makeText(this, "Nhấn thoát lại lần nữa", Toast.LENGTH_SHORT).show();
        }
        pressBack = System.currentTimeMillis();
    }

    public abstract String getKey();

    protected String getType(int type) {
        NotificationType t = NotificationType.getType(type);
        switch (t) {
            case ADVICE:
                return RECORD;
            case REPLY:
                return INQUIRY;
            case REMOVE_ASSIGN:
                return DISOWN;
            case SCHEDULE:
            case SCHEDULE_REMINDER:
                return SCHEDULE;
            default:
                return null;
        }
    }
}
