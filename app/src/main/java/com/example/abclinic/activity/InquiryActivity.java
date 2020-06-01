package com.example.abclinic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.NotificationType;
import com.abclinic.constant.PayloadStatus;
import com.abclinic.constant.StorageConstant;
import com.abclinic.entity.Inquiry;
import com.abclinic.entity.Record;
import com.abclinic.entity.Reply;
import com.abclinic.retrofit.api.ImageMapper;
import com.abclinic.retrofit.api.InquiryMapper;
import com.abclinic.retrofit.api.RecordMapper;
import com.abclinic.utils.DateTimeUtils;
import com.abclinic.utils.services.JsonService;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.abclinic.R;
import com.example.abclinic.adapter.ViewRecordAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class InquiryActivity extends CustomActivity {
    NestedScrollView scrollView;
    TextView createdTimeTxt, statusTxt, descTxt, dateTxt;
    ImageSlider imageSlider;
    RecyclerView recyclerView;
    ViewRecordAdapter viewRecordAdapter;
    FloatingActionButton actionButton;

    private LinearLayoutManager linearLayoutManager;
    private int type;
    private long id;
    private Inquiry inquiry;
    private Record record;
    private Reply reply;
    private List<String> images;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_INQUIRY_RESULT;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        scrollView = findViewById(R.id.scroll_inquiry);
        statusTxt = findViewById(R.id.statusTxt);
        createdTimeTxt = findViewById(R.id.createview);
        dateTxt = findViewById(R.id.dateview);
        descTxt = findViewById(R.id.descriptxt);
        imageSlider = findViewById(R.id.carousel);
        recyclerView = findViewById(R.id.recycler_record);
        actionButton = findViewById(R.id.float_btn);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false) {
        };
        recyclerView.setLayoutManager(linearLayoutManager);

        type = getIntent().getIntExtra(Constant.TYPE, -1);
        id = getIntent().getLongExtra(Constant.PAYLOAD_ID, 0);
        Log.d(Constant.DEBUG_TAG, "Type: " + type + ", id: " + id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionButton.setOnClickListener((v) -> {
            Snackbar.make(scrollView, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        actionButton.setOnTouchListener(new View.OnTouchListener() {

            float startX;
            float startRawX;
            float distanceX;
            int lastAction;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = view.getX() - event.getRawX();
                        startRawX = event.getRawX();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setX(event.getRawX() + startX);
                        view.setY(event.getRawY() + startX);

                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        distanceX = event.getRawX() - startRawX;
                        if (Math.abs(distanceX) < 10) {
//                            Toast.makeText(InquiryActivity.this, "FAB CLICKED", Toast.LENGTH_SHORT).show();
                            startActivity(makeReplyIntent());
                        }

                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        startActivity(makeReplyIntent());
                    default:
                        return false;
                }
                return true;
            }
        });
        handleResponse(NotificationType.getType(type), id);
    }

    private Intent makeReplyIntent() {
        Intent intent = new Intent(InquiryActivity.this, ReplyActivity.class);
        String json = JsonService.toString(inquiry.getReplies());
        intent.putExtra(Constant.REPLIES, json);
        intent.putExtra(Constant.INQUIRY_ID, inquiry.getId());
        startActivity(intent);
        return intent;
    }

    private void showImageFull(int i) {
        new ImageViewer.Builder<>(InquiryActivity.this, images)
                .setStartPosition(i)
                .allowSwipeToDismiss(true)
                .show();
    }

    private void handleResponse(NotificationType type, long id) {
        switch (type) {
            case REPLY:
            case INQUIRY:
                getInquiry(id);
                break;
            case ADVICE:
                getRecord(id);
                break;
        }
    }

    private void getInquiry(long id) {
        Call<Inquiry> call = retrofit.create(InquiryMapper.class).getInquiry(id);
        call.enqueue(new CustomCallback<Inquiry>(this) {
            @Override
            protected void processResponse(Response<Inquiry> response) {
                inquiry = response.body();
                if (inquiry.getAlbumId() != null)
                    getImages(inquiry.getAlbumId());
                else imageSlider.setVisibility(View.GONE);
                try {
                    PayloadStatus status = inquiry.getStatusValue();
                    statusTxt.setText(status.getMessage());
                    statusTxt.setTextColor(ContextCompat.getColor(context, status.getColorId()));
                } catch (Throwable t) {
                    Toast.makeText(InquiryActivity.this, "Trạng thái không hợp lệ", Toast.LENGTH_LONG).show();
                }
                createdTimeTxt.setText(DateTimeUtils.toString(inquiry.getCreatedAt()));
                dateTxt.setText(DateTimeUtils.toString(inquiry.getDate()));
                descTxt.setText(inquiry.getContent());
                viewRecordAdapter = new ViewRecordAdapter(InquiryActivity.this, inquiry);
                recyclerView.setAdapter(viewRecordAdapter);
                if (record != null) {
                    int pos = -1;
                    inquiry.sort();
                    switch (record.getType()) {
                        case Constant.MEDICAL_RECORD:
                            pos = inquiry.getMedicalRecords().indexOf(record);
                            break;
                        case Constant.DIET_RECORD:
                            pos = inquiry.getDietRecords().indexOf(record);
                            break;
                    }
                    if (pos >= 0)
                        focusOn(pos);
                }
            }
        }.handle(404, R.string.not_found_err, "yêu cầu"));
    }

    private void getRecord(long id) {
        Call<Record> call = retrofit.create(RecordMapper.class).getRecord(id);
        call.enqueue(new CustomCallback<Record>(this) {
            @Override
            protected void processResponse(Response<Record> response) {
                record = response.body();
                getInquiry(record.getInquiry().getId());
            }
        }.handle(404, R.string.not_found_err, "tư vấn"));
    }

    private void getImages(String albumId) {
        Call<List<String>> call = retrofit.create(ImageMapper.class).getImages(albumId);
        call.enqueue(new CustomCallback<List<String>>(this) {
            @Override
            protected void processResponse(Response<List<String>> response) {
                images = response.body();
                startCarousel(images);
            }
        });
    }


//
//    private void setupRecords(Inquiry inquiry) {
//        recordPagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(),
//                "Chi tiết ghi chú",
//                "Chi tiết chẩn đoán\nChi tiết chẩn đoán\nChi tiết chẩn đoán\nChi tiết chẩn đoán\nChi tiết chẩn đoán\n",
//                "Chi tiết kê đơn");
//        viewPager.setAdapter(recordPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//    }

    private void startCarousel(List<String> images) {
        List<SlideModel> models = images.stream().map(SlideModel::new).collect(Collectors.toList());
        imageSlider.setImageList(models, true);
        imageSlider.setItemClickListener(this::showImageFull);
    }

    private void focusOn(int pos) {

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        storageService.saveCache(StorageConstant.KEY_TYPE, type);
        storageService.saveCache(StorageConstant.KEY_ID, id);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
