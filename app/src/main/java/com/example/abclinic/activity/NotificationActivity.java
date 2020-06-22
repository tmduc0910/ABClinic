package com.example.abclinic.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.NotificationType;
import com.abclinic.constant.StorageConstant;
import com.abclinic.dto.NotificationListDto;
import com.abclinic.dto.PushNotificationDto;
import com.abclinic.entity.Notification;
import com.abclinic.retrofit.api.NotificationMapper;
import com.abclinic.utils.services.LocalStorageService;
import com.abclinic.utils.services.MyFirebaseService;
import com.abclinic.utils.services.intent.job.GetNotificationJob;
import com.abclinic.utils.services.intent.job.Receiver;
import com.abclinic.utils.services.intent.job.ServiceResultReceiver;
import com.abclinic.websocket.observer.IObserver;
import com.example.abclinic.NotificationIntent;
import com.example.abclinic.PaginationListener;
import com.example.abclinic.R;
import com.example.abclinic.adapter.ViewNotificationAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationActivity extends CustomActivity implements Receiver {
    private static final int PAGE_SIZE = 20;

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    ViewNotificationAdapter viewNotificationAdapter;
    BottomNavigationView bottomNav;

    private LinearLayoutManager layoutManager;
    private NotificationListDto list = new NotificationListDto();
    private boolean isLoading = false;
    private boolean isInit = false;
    private BroadcastReceiver receiver;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_NOTI;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isInit = extras.getBoolean("isInit");
            if (isInit) {
                setTheme(android.R.style.Theme_NoDisplay);
                receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        fetchNotification();
                    }
                };
                IntentFilter filter = new IntentFilter(FETCH_HISTORY);
                LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
                Intent data = new Intent();
                data.putExtra("caller", "notification");
                data.putExtra("isLast", GetNotificationJob.isLast);
                setResult(RESULT_OK, data);
                finish();
            }

            String content = extras.getString("content");
            try {
                PushNotificationDto dto = new ObjectMapper().readValue(content, PushNotificationDto.class);
//                setTheme(android.R.style.Theme_NoDisplay);
                Call<Notification> call = retrofit.create(NotificationMapper.class)
                        .getNotification(dto.getNotificationId());
                call.enqueue(new CustomCallback<Notification>(this) {
                    @Override
                    protected void processResponse(Response<Notification> response) {
                        switchActivity(response.body());
                    }

                    @Override
                    protected boolean useDialog() {
                        return false;
                    }
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
//            finish();
        }

        setContentView(R.layout.activity_notification);
        storageService = new LocalStorageService(this, StorageConstant.STORAGE_KEY_NOTI);
        recyclerView = findViewById(R.id.recycler_noti);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout = findViewById(R.id.swipe_refresh);

        //bottomnavigationbar
        bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        IObserver<PushNotificationDto> notiObserver = obj -> {
            BadgeDrawable badge = bottomNav.getOrCreateBadge(R.id.notifi);
            badge.setVisible(true);
            hasNewNoti = true;
        };
        MyFirebaseService.subject.attach(notiObserver);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.upload:
                    startActivity(new Intent(NotificationActivity.this, UpLoadActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;
                case R.id.notifi:
                    break;
                case R.id.history:
                    startActivity(new Intent(NotificationActivity.this, HistoryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.profile:
                    startActivity(new Intent(NotificationActivity.this, ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
            }
            return false;
        });

        GetNotificationJob.page = PaginationListener.PAGE_START;
        GetNotificationJob.isLast = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasNewNoti)
            bottomNav.getOrCreateBadge(R.id.notifi).setVisible(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading && !GetNotificationJob.isLast) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == list.size() - 1) {
                        if (layoutManager.findLastCompletelyVisibleItemPosition() == list.size() - 1) {
                            Log.d(Constant.DEBUG_TAG, "Log: " + layoutManager.findLastCompletelyVisibleItemPosition());
                        }
                        fetchNotification();
                        isLoading = true;
                    }
                }
            }
        });
        refreshLayout.setOnRefreshListener(() -> {
            try {
                list = new NotificationListDto();
                GetNotificationJob.page = PaginationListener.PAGE_START;
                GetNotificationJob.isLast = false;
                viewNotificationAdapter = null;
                fetchNotification();
            } finally {
                refreshLayout.setRefreshing(false);
                Toast.makeText(NotificationActivity.this, "Làm mới thành công", Toast.LENGTH_SHORT).show();
            }
        });
        observer = push -> fetchNotification();
        MyFirebaseService.subject.attach(observer);
        fetchNotification();
    }

    public void initAdapter() {
        viewNotificationAdapter = new ViewNotificationAdapter(this, list);
        viewNotificationAdapter.setOnClickListener((v) -> {
            final int pos = recyclerView.getChildLayoutPosition(v);
            Log.d(Constant.DEBUG_TAG, "Position: " + pos);
            Call<Notification> call = retrofit.create(NotificationMapper.class).getNotification(list.get(pos).getId());
            call.enqueue(new CustomCallback<Notification>(this) {
                @Override
                protected void processResponse(Response<Notification> response) {
                    Notification n = response.body();
                    list.get(pos).setIsRead(true);
                    viewNotificationAdapter.notifyDataSetChanged();

                    switchActivity(n);
                }
            });
        });
        recyclerView.setAdapter(viewNotificationAdapter);
    }

    private void switchActivity(Notification n) {
        Class c;
        switch (NotificationType.getType(n.getType())) {
            case INQUIRY:
            case REPLY:
            case MED_ADVICE:
            case DIET_ADVICE:
                c = InquiryActivity.class;
                break;
            case SCHEDULE_REMINDER:
            case SCHEDULE:
                c = UploadHealthResultActivity.class;
                break;
            default:
                return;
        }
        startActivity(new NotificationIntent(NotificationActivity.this,
                c,
                n.getType(),
                n.getPayloadId()));
    }

    private void fetchNotification() {
        hasNewNoti = false;
        if (!GetNotificationJob.isLast) {
            runOnUiThread(() -> {
                ServiceResultReceiver receiver = new ServiceResultReceiver(new Handler());
                receiver.setReceiver(this);
                GetNotificationJob.enqueueWork(this, receiver);
            });
        }
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        list.addItems(false, ((NotificationListDto) resultData.getSerializable("notifications")).getList());
        if (!isInit) {
            if (viewNotificationAdapter == null)
                initAdapter();
            else {
                viewNotificationAdapter.notifyDataSetChanged();
            }
            isLoading = false;
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }
}
