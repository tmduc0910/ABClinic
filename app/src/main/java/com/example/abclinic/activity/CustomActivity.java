package com.example.abclinic.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.NotificationType;
import com.abclinic.dto.PushNotificationDto;
import com.abclinic.entity.Inquiry;
import com.abclinic.entity.UserInfo;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.InquiryMapper;
import com.abclinic.room.entity.DataEntity;
import com.abclinic.room.utils.AppDatabase;
import com.abclinic.utils.services.LocalStorageService;
import com.abclinic.utils.services.MyFirebaseService;
import com.abclinic.utils.services.PermissionUtils;
import com.abclinic.websocket.observer.IObserver;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class CustomActivity extends AppCompatActivity {
    protected static final int CODE_LOGOUT = 100;
    protected static final String FETCH_HISTORY = "fetchHistory";
    protected static final int CODE_FETCH_HISTORY = 333;

    protected LocalStorageService storageService;
    protected Retrofit retrofit;
    protected PermissionUtils permissionUtils;
    protected AppDatabase appDatabase;
    protected IObserver<PushNotificationDto> observer;
    protected UserInfo userInfo;
    private long pressBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageService = new LocalStorageService(this, getKey());
        retrofit = RetrofitClient.getClient(storageService.getUid());
        appDatabase = AppDatabase.getInstance(this);
        if (!(this instanceof LoginActivity))
            userInfo = storageService.getUserInfo();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (observer != null)
            MyFirebaseService.subject.detach(observer);
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

    protected void getInquiries(int month, int year) {
        Call<List<Inquiry>> call = retrofit.create(InquiryMapper.class).getInquiryListByMonth(month, year);
        call.enqueue(new CustomCallback<List<Inquiry>>(this) {
            @Override
            protected void processResponse(Response<List<Inquiry>> response) {
                AsyncTask.execute(() -> {
                    List<DataEntity> latestList = appDatabase.getDataDao().getAllByType(NotificationType.INQUIRY.getValue());
                    List<Inquiry> results = response.body();

                    results.removeAll(latestList);
                    UserInfo userInfo = storageService.getUserInfo();
                    appDatabase.getDataDao().save(results.stream()
                            .map(r -> new DataEntity(userInfo.getId(),
                                    NotificationType.INQUIRY.getValue(),
                                    r.getId(),
                                    r.getCreatedAt()))
                            .toArray(DataEntity[]::new));
                });
            }

            @Override
            protected boolean useDialog() {
                return false;
            }
        });
    }
    public abstract String getKey();
}
