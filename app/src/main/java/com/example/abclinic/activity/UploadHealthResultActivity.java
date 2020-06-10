package com.example.abclinic.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.HttpStatus;
import com.abclinic.constant.StorageConstant;
import com.abclinic.cor.AbstractTimeCalculator;
import com.abclinic.dto.IndexResultRequestDto;
import com.abclinic.dto.RequestCreateResultDto;
import com.abclinic.dto.TextValueDto;
import com.abclinic.dto.TimeDto;
import com.abclinic.entity.HealthIndexSchedule;
import com.abclinic.retrofit.api.HealthIndexMapper;
import com.abclinic.utils.services.intent.job.GetScheduleJob;
import com.abclinic.utils.services.intent.job.Receiver;
import com.abclinic.utils.services.intent.job.ServiceResultReceiver;
import com.abclinic.websocket.observer.IObserver;
import com.example.abclinic.R;
import com.example.abclinic.adapter.ViewIndexFieldAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class UploadHealthResultActivity extends CustomActivity implements Receiver {
    RadioGroup schedulesRadio;
    RecyclerView recyclerView;
    FloatingActionButton submitBtn;
    ViewIndexFieldAdapter viewIndexFieldAdapter;
    RadioGroup.LayoutParams radioParams;

    private List<HealthIndexSchedule> list;
    private IObserver<TextValueDto> textObserver;
    private Map<Long, String> cacheResults = new TreeMap<>();
    private long defaultScheduleId = -1;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_UPLOAD_RESULT;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_health_index);
        schedulesRadio = findViewById(R.id.schedules_radio);
        recyclerView = findViewById(R.id.recycler_fields);
        submitBtn = findViewById(R.id.result_upload);
        schedulesRadio.setOrientation(LinearLayout.VERTICAL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(UploadHealthResultActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);

        textObserver = obj -> {
            cacheResults.put(obj.getId(), obj.getValue());
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        long userId = storageService.getUserInfo().getId();
        ServiceResultReceiver receiver = new ServiceResultReceiver(new Handler());
        receiver.setReceiver(this);
        GetScheduleJob.enqueueWork(this, receiver);

        schedulesRadio.setOnCheckedChangeListener((v, id) -> {
            int pos = getSelectedPosition();
            viewIndexFieldAdapter = new ViewIndexFieldAdapter(this, list.get(pos).getIndex());
            viewIndexFieldAdapter.addObserver(textObserver);
            recyclerView.setAdapter(viewIndexFieldAdapter);
        });

        submitBtn.setOnClickListener(v -> {
            if (list != null && !list.isEmpty()) {
                SweetAlertDialog confirmDialog = new SweetAlertDialog(UploadHealthResultActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Bạn chắc chắn muốn gửi chưa?")
                        .setConfirmButton("Có", d -> {
                            d.dismissWithAnimation();
                            int pos = getSelectedPosition();
                            HealthIndexSchedule s = list.get(pos);
                            List<IndexResultRequestDto> dtos = s.getIndex().getFields().stream()
                                    .filter(f -> cacheResults.keySet().contains(f.getId()))
                                    .map(f -> new IndexResultRequestDto(f.getId(), cacheResults.get(f.getId())))
                                    .collect(Collectors.toList());

                            if (dtos.size() == s.getIndex().getFields().size()) {
                                Call<Void> call = retrofit.create(HealthIndexMapper.class)
                                        .uploadResult(new RequestCreateResultDto(s.getId(), dtos));
                                call.enqueue(new CustomCallback<Void>(UploadHealthResultActivity.this) {
                                    @Override
                                    protected void processResponse(Response<Void> response) {
                                        SweetAlertDialog dialog = new SweetAlertDialog(UploadHealthResultActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Thông báo")
                                                .setContentText("Gửi kết quả thành công!!");
                                        dialog.show();
                                        dialog.setConfirmButton("OK", d -> {
                                            d.dismissWithAnimation();
                                            finish();
                                        });
                                    }
                                }.handle(HttpStatus.NOT_FOUND, R.string.not_found_err, "lịch nhắc")
                                        .handle(HttpStatus.BAD_REQUEST, R.string.result_forbidden));
                            } else
                                Snackbar.make(recyclerView, "Bạn phải nhập toàn bộ các trường kết quả!!!", Snackbar.LENGTH_LONG).show();
                        })
                        .setCancelButton("Không", SweetAlertDialog::dismissWithAnimation);
                confirmDialog.show();
            } else {
                SweetAlertDialog dialog = new SweetAlertDialog(UploadHealthResultActivity.this, SweetAlertDialog.ERROR_TYPE);
                dialog.setTitleText("Lỗi")
                        .setContentText("Bạn chưa có lịch nhắc nhở!")
                        .show();
            }
        });
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        list = resultData.getParcelableArrayList("schedules");
        list.forEach(s -> {
            RadioButton btn = new RadioButton(this);
            TimeDto timeDto = AbstractTimeCalculator.getCalculator().execute(LocalDateTime.now(), s.getEndedAt());
            StringJoiner stringJoiner = new StringJoiner("\n");
            stringJoiner.add(s.getIndex().getName());
            stringJoiner.add(s.getIndex().getDescription());
            stringJoiner.add(String.format(Locale.getDefault(), "Còn %d %s nữa", timeDto.getValue(), timeDto.getTimeUnit()));
            btn.setText(stringJoiner.toString());
            btn.setTextSize(18);
            int viewId = View.generateViewId();
            btn.setId(viewId);
            btn.setPadding(0, 50, 500, 0);
            radioParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            schedulesRadio.addView(btn, radioParams);

            if (s.getId() == defaultScheduleId)
                schedulesRadio.check(viewId);
        });
    }

    private int getSelectedPosition() {
        return schedulesRadio.indexOfChild(findViewById(schedulesRadio.getCheckedRadioButtonId()));
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
