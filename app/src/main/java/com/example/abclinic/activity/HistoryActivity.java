package com.example.abclinic.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abclinic.constant.NotificationType;
import com.abclinic.dto.NotificationListDto;
import com.abclinic.dto.PushNotificationDto;
import com.abclinic.entity.Notification;
import com.abclinic.entity.UserInfo;
import com.abclinic.room.entity.DataEntity;
import com.abclinic.room.entity.ScheduleEntity;
import com.abclinic.utils.DateTimeUtils;
import com.abclinic.utils.services.MyFirebaseService;
import com.abclinic.utils.services.intent.job.GetNotificationJob;
import com.abclinic.utils.services.intent.job.Receiver;
import com.abclinic.utils.services.intent.job.ServiceResultReceiver;
import com.abclinic.websocket.observer.IObserver;
import com.applandeo.materialcalendarview.CalendarView;
import com.example.abclinic.CustomEventDay;
import com.example.abclinic.NotificationIntent;
import com.example.abclinic.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HistoryActivity extends CustomActivity implements Receiver {
    private static int month, year;
    CalendarView calendarView;
    Button resultSubmit;
    BottomNavigationView bottomNav;

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
        bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        IObserver<PushNotificationDto> notiObserver = obj -> {
            BadgeDrawable badge = bottomNav.getOrCreateBadge(R.id.notifi);
            badge.setVisible(true);
            hasNewNoti = true;
        };
        MyFirebaseService.subject.attach(notiObserver);

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

                        BottomNavigationView bottomNav = findViewById(R.id.navigation);
                        BadgeDrawable badge = bottomNav.getBadge(R.id.notifi);
                        if (badge != null) {
                            badge.clearNumber();
                            hasNewNoti = false;
                        }
                        break;
                    case R.id.history:

                        break;
                    case R.id.profile:
                        Intent intent_home = new Intent(HistoryActivity.this, ProfileActivity.class);
                        startActivity(intent_home);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasNewNoti)
            bottomNav.getOrCreateBadge(R.id.notifi).setVisible(true);
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
                makeHistoryDialog(ev);
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
                        for (DataEntity v : Objects.requireNonNull(map.get(getKey(month, year)))) {
                            NotificationType type = NotificationType.getType(v.getType());
                            CustomEventDay.IconType iconType = null;
                            switch (type) {
                                case INQUIRY:
                                    iconType = CustomEventDay.IconType.INQUIRY;
                                    break;
                                case MED_ADVICE:
                                    iconType = CustomEventDay.IconType.MED_RECORD;
                                    break;
                                case DIET_ADVICE:
                                    iconType = CustomEventDay.IconType.DIET_RECORD;
                                    break;
                                case REPLY:
                                    iconType = CustomEventDay.IconType.REPLY;
                                    break;
                            }
                            putIcon(iconType, DateTimeUtils.toCalendar(v.getDate()));
                        }
                        calendarView.setEvents(events.values()
                                .stream()
                                .map(CustomEventDay.Builder::build)
                                .collect(Collectors.toList()));
                    });

            appDatabase.getScheduleDao().getAvailableSchedules(userInfo.getId())
                    .observe(this, list -> {
                        for (ScheduleEntity s : list) {
                            if (s.getEndedAt().isAfter(LocalDateTime.now()))
                                putIcon(CustomEventDay.IconType.SCHEDULE, DateTimeUtils.toCalendar(s.getEndedAt()));
                        }
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

    private void makeHistoryDialog(CustomEventDay eventDay) {
        int[] pos = new int[1];
        String[] items = eventDay.getIcons().stream()
                .map(CustomEventDay.IconType::getMeaning)
                .toArray(String[]::new);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo trong ngày")
                .setPositiveButton("Tiếp", (d, which) -> {
                    d.cancel();
                    List<String> detailItems = new ArrayList<>();
                    SweetAlertDialog dialog = new SweetAlertDialog(HistoryActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    dialog.setCancelable(false);
                    dialog.show();

                    LocalDate date = DateTimeUtils.parseDate(eventDay.getCalendar());
                    AsyncTask.execute(() -> {
                        if (eventDay.getIcons().get(pos[0]).getType() == NotificationType.SCHEDULE_REMINDER.getValue()) {
                            List<ScheduleEntity> list = appDatabase.getScheduleDao().getStaticAvailableSchedules(userInfo.getId());
                            list.stream()
                                    .filter(s -> s.getEndedAt().toLocalDate().equals(date))
                                    .forEach(s -> {
                                        detailItems.add(String.format("Cần gửi chỉ số %s", s.getIndexName()));
                                    });
                            dialog.dismissWithAnimation();
                            makeDetailDialog(eventDay,
                                    "Lịch nhắc nhở",
                                    detailItems,
                                    NotificationType.SCHEDULE_REMINDER.getValue(),
                                    list.stream()
                                            .filter(s -> s.getEndedAt().toLocalDate().equals(date))
                                            .map(ScheduleEntity::getScheduleId)
                                            .collect(Collectors.toList()));

                        } else {
                            List<DataEntity> datas = appDatabase.getDataDao().getDatas(userInfo.getId(),
                                    eventDay.getIcons().get(pos[0]).getType(),
                                    date.getDayOfMonth(),
                                    date.getMonthValue(),
                                    date.getYear());
                            String[] s = new String[1];
                            if (datas.get(0).getType() == NotificationType.INQUIRY.getValue())
                                s[0] = "Đã gửi";
                            else s[0] = "Đã nhận";
                            for (DataEntity data : datas) {
                                detailItems.add(String.format("%s lúc %s", s[0], data.getDate().toLocalTime().toString()));
                            }
                            dialog.dismissWithAnimation();
                            makeDetailDialog(eventDay,
                                    eventDay.getIcons().get(pos[0]).getMeaning(),
                                    detailItems,
                                    datas.get(0).getType(),
                                    datas.stream()
                                            .map(DataEntity::getPayloadId)
                                            .collect(Collectors.toList()));
                        }
                    });
                })
                .setNegativeButton("Hủy", (d, which) -> d.cancel())
                .setSingleChoiceItems(items, 0, (d, which) -> {
                    pos[0] = which;
                });
        final AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogInAnimation;
        alertDialog.show();
    }

    private void makeDetailDialog(CustomEventDay eventDay, String title, List<String> contents, int type, List<Long> payloadIds) {
        int[] pos = new int[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(String.format("%s ngày %s", title, DateTimeUtils.toString(eventDay.getDate())))
                .setPositiveButton("Chọn", (d, which) -> {
                    d.cancel();
                    if (type == NotificationType.SCHEDULE_REMINDER.getValue()) {
                        Intent intent = new Intent(HistoryActivity.this, UploadHealthResultActivity.class);
                        intent.putExtra("defaultId", payloadIds.get(pos[0]));
                        startActivity(intent);
                    } else {
                        long id = payloadIds.get(pos[0]);
                        startActivity(new NotificationIntent(HistoryActivity.this, InquiryActivity.class, type, id));
                    }
                })
                .setNegativeButton("Quay lại", (d, which) -> {
                    d.cancel();
                    makeHistoryDialog(eventDay);
                })
                .setSingleChoiceItems(contents.toArray(new String[0]), 0, (d, which) -> {
                    pos[0] = which;
                });

        runOnUiThread(() -> {
            AlertDialog alertDialog = builder.create();
            if (alertDialog.getWindow() != null)
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogOutAnimation;
            alertDialog.show();
        });
    }
}
