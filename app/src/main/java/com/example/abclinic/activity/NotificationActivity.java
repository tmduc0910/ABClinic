package com.example.abclinic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.NotificationType;
import com.abclinic.constant.StorageConstant;
import com.abclinic.dto.NotificationListDto;
import com.abclinic.entity.Notification;
import com.abclinic.entity.PageableEntity;
import com.abclinic.retrofit.api.NotificationMapper;
import com.abclinic.utils.services.LocalStorageService;
import com.example.abclinic.NotificationIntent;
import com.example.abclinic.PaginationListener;
import com.example.abclinic.R;
import com.example.abclinic.adapter.ViewNotificationAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Response;

public class NotificationActivity extends CustomActivity {
    private static final int PAGE_SIZE = 20;

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    ViewNotificationAdapter viewNotificationAdapter;

    private LinearLayoutManager layoutManager;
    private PageableEntity<Notification> notifications;
    private NotificationListDto list = new NotificationListDto();
    private boolean isLoading = false;
    private boolean isLast = false;
    private int page = PaginationListener.PAGE_START;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_NOTI;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        storageService = new LocalStorageService(this, StorageConstant.STORAGE_KEY_NOTI);
        recyclerView = findViewById(R.id.recycler_noti);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout = findViewById(R.id.swipe_refresh);

        //bottomnavigationbar
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:
                        startActivity(new Intent(NotificationActivity.this, UpLoadActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.notifi:
                        break;
                    case R.id.history:
                        startActivity(new Intent(NotificationActivity.this, HistoryActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(NotificationActivity.this, ProfileActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading && !isLast) {
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
                page = PaginationListener.PAGE_START;
                viewNotificationAdapter = null;
                fetchNotification();
            } finally {
                refreshLayout.setRefreshing(false);
                Toast.makeText(NotificationActivity.this, "Làm mới thành công", Toast.LENGTH_SHORT).show();
            }
        });
        fetchNotification();
    }

    public void initAdapter() {
        viewNotificationAdapter = new ViewNotificationAdapter(this, list);
        viewNotificationAdapter.setOnClickListener((v) -> {
            final int pos = recyclerView.getChildLayoutPosition(v);
            Log.d(Constant.DEBUG_TAG, "Position: " + pos);
            Call<Notification> call = retrofit.create(NotificationMapper.class).getNotification(list.get(pos).getId());
            call.enqueue(new CustomCallback<Notification>(this, StorageConstant.STORAGE_KEY_NOTI) {
                @Override
                protected void processResponse(Response<Notification> response) {
                    Notification n = response.body();
                    list.get(pos).setIsRead(true);
                    viewNotificationAdapter.notifyDataSetChanged();

                    Class c = null;
                    switch (NotificationType.getType(n.getType())) {
                        case INQUIRY:
                        case REPLY:
                        case ADVICE:
                        case UPDATE_ADVICE:
                            c = InquiryActivity.class;
                            break;
                        case SCHEDULE_REMINDER:
                        case SCHEDULE:
                            return;
                        default:
                            return;
                    }
                    startActivity(new NotificationIntent(NotificationActivity.this,
                            c,
                            n.getType(),
                            n.getPayloadId()));
                }
            });
        });
        recyclerView.setAdapter(viewNotificationAdapter);
    }

    private void fetchNotification() {
        Call<PageableEntity<Notification>> call = retrofit.create(NotificationMapper.class).getNotificationList(page, PAGE_SIZE);
        call.enqueue(new CustomCallback<PageableEntity<Notification>>(this, StorageConstant.STORAGE_KEY_NOTI) {
            @Override
            protected void processResponse(Response<PageableEntity<Notification>> response) {
                notifications = response.body();
                storageService.saveCache(StorageConstant.KEY_NOTIFICATION, notifications.toString());
                isLast = notifications.getTotalPages() <= page;
                Log.d(Constant.DEBUG_TAG, "Loading = " + isLoading + ", Last = " + isLast);

                list.addItems(false, notifications.getContent());

                if (viewNotificationAdapter == null)
                    initAdapter();
                else {
                    viewNotificationAdapter.notifyDataSetChanged();
                }
                isLoading = false;
                page++;
            }
        });
    }
}
