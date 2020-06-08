package com.example.abclinic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abclinic.constant.NotificationType;
import com.abclinic.dto.NotificationListDto;
import com.abclinic.entity.Notification;
import com.abclinic.entity.UserInfo;
import com.abclinic.room.entity.DataEntity;
import com.abclinic.utils.DateTimeUtils;
import com.abclinic.utils.services.intent.job.GetNotificationJob;
import com.abclinic.utils.services.intent.job.Receiver;
import com.abclinic.utils.services.intent.job.ServiceResultReceiver;
import com.applandeo.materialcalendarview.CalendarView;
import com.example.abclinic.CustomEventDay;
import com.example.abclinic.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HistoryActivity extends CustomActivity implements Receiver {
    private static int month, year;
    CalendarView calendarView;
    Button resultSubmit;
    private HashMap<String, List<DataEntity>> map = new HashMap<>();
    private UserInfo userInfo;
    private Map<Integer, CustomEventDay.Builder> events = new TreeMap<>();
    private ServiceResultReceiver resultReceiver;

    @Override
    public String getKey() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        calendarView = findViewById(R.id.calendarView);
        resultSubmit = findViewById(R.id.resultSubmit);
        userInfo = storageService.getUserInfo();

        month = LocalDateTime.now().getMonthValue();
        year = LocalDateTime.now().getYear();
        requireData();

        //bottomnavigationbar
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:
                        Intent intent_acc = new Intent(HistoryActivity.this, UpLoadActivity.class);
                        startActivity(intent_acc);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.notifi:
                        Intent intent_mess = new Intent(HistoryActivity.this, NotificationActivity.class);
                        startActivity(intent_mess);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.history:

                        break;
                    case R.id.profile:
                        Intent intent_home = new Intent(HistoryActivity.this, ProfileActivity.class);
                        startActivity(intent_home);
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
        updateCalendar(month, year);

        calendarView.setOnForwardPageChangeListener(() -> {
            if (month == 12) {
                month = 1;
                year++;
            } else month++;
            updateCalendar(month, year);
        });

        calendarView.setOnPreviousPageChangeListener(() -> {
            if (month == 1) {
                month = 12;
                year--;
            } else month--;
            updateCalendar(month, year);
        });

        calendarView.setOnDayClickListener(eventDay -> {
            if (eventDay instanceof CustomEventDay) {
                CustomEventDay ev = (CustomEventDay) eventDay;
                if (ev.containsIcon(CustomEventDay.IconType.SCHEDULE)) {
                    startActivity(new Intent(HistoryActivity.this, UploadHealthResultActivity.class));
                }
            } else {
                SweetAlertDialog dialog = new SweetAlertDialog(HistoryActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("Không có thông báo nào");
                dialog.show();
            }
        });

        resultSubmit.setOnClickListener((v) -> {
            startActivity(new Intent(HistoryActivity.this, UploadHealthResultActivity.class));
        });
    }

    private String getKey(int month, int year) {
        return String.format("%s%s", month, year);
    }

    private void updateCalendar(int month, int year) {
        getInquiries(month, year);
        if (map.get(getKey(month, year)) == null) {
            requireData();
            appDatabase.getDataDao().getDatas(userInfo.getId(), month, year)
                    .observe(this, list -> {
                        map.put(getKey(month, year), list);
                        map.get(getKey(month, year)).forEach(v -> {
                            NotificationType type = NotificationType.getType(v.getType());
                            CustomEventDay.IconType iconType = null;
                            switch (type) {
                                case INQUIRY:
                                    iconType = CustomEventDay.IconType.INQUIRY;
                                    break;
                                case MED_ADVICE:
                                    iconType = CustomEventDay.IconType.RECORD;
                                    break;
                                case REPLY:
                                    iconType = CustomEventDay.IconType.REPLY;
                                    break;
                            }
                            putIcon(iconType, DateTimeUtils.toCalendar(v.getDate()));
                        });
                        calendarView.setEvents(events.values()
                                .stream()
                                .map(CustomEventDay.Builder::build)
                                .collect(Collectors.toList()));
                    });

            appDatabase.getScheduleDao().getAvailableSchedules(userInfo.getId())
                    .observe(this, list -> {
                        list.forEach(s -> {
                            if (s.getEndedAt().isAfter(LocalDateTime.now()))
                                putIcon(CustomEventDay.IconType.SCHEDULE, DateTimeUtils.toCalendar(s.getEndedAt()));
                        });
                        calendarView.setEvents(events.values()
                                .stream()
                                .map(CustomEventDay.Builder::build)
                                .collect(Collectors.toList()));
                    });
        } else {
            calendarView.setEvents(events.values()
                    .stream()
                    .map(CustomEventDay.Builder::build)
                    .collect(Collectors.toList()));
        }
    }

    private void putIcon(CustomEventDay.IconType iconType, Calendar c) {
        if (iconType != null) {
            CustomEventDay.Builder builder = events.get(c.get(Calendar.DAY_OF_MONTH));
            if (builder == null) {
                builder = new CustomEventDay.Builder(this)
                        .setCalendar(c);
            }
            builder = builder.display(iconType);
            events.put(c.get(Calendar.DAY_OF_MONTH), builder);
        }
    }

    private void requireData() {
        resultReceiver = new ServiceResultReceiver(new Handler());
        resultReceiver.setReceiver(this);
        GetNotificationJob.enqueueWork(this, resultReceiver);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        List<Notification> list = ((NotificationListDto) resultData.getSerializable("notifications")).getList();
        list.stream()
                .min((o1, o2) -> o1.getCreatedAt().compareTo(o2.getCreatedAt()))
                .ifPresent((last) -> {
                    if (last.getCreatedAt().isAfter(LocalDateTime.of(year, month, 1, 0, 0, 0)) &&
                            !GetNotificationJob.isLast) {
                        requireData();
                    }
                });
    }
}
