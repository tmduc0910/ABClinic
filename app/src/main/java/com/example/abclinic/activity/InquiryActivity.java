package com.example.abclinic.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.NotificationType;
import com.abclinic.constant.PayloadStatus;
import com.abclinic.constant.StorageConstant;
import com.abclinic.entity.Inquiry;
import com.abclinic.retrofit.api.ImageMapper;
import com.abclinic.retrofit.api.InquiryMapper;
import com.abclinic.utils.DateTimeUtils;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.abclinic.R;
import com.example.abclinic.adapter.ViewRecordAdapter;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class InquiryActivity extends CustomActivity {
    TextView timeTxt, statusTxt, descTxt;
    ImageSlider imageSlider;
    RecyclerView recyclerView;
    ViewRecordAdapter viewRecordAdapter;

    private LinearLayoutManager linearLayoutManager;
    private int type;
    private long id;
    private Inquiry inquiry;
    private List<String> images;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_INQUIRY_RESULT;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        statusTxt = findViewById(R.id.statusTxt);
        timeTxt = findViewById(R.id.timeview);
        descTxt = findViewById(R.id.descriptxt);
        imageSlider = findViewById(R.id.carousel);
        recyclerView = findViewById(R.id.recycler_record);
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
        getInquiry(NotificationType.getType(type), id);
    }

    private void showImageFull(int i) {
        new ImageViewer.Builder<>(InquiryActivity.this, images)
                .setStartPosition(i)
                .allowSwipeToDismiss(true)
                .show();
    }

    private void getInquiry(NotificationType type, long id) {
        switch (type) {
            case REPLY:
            case INQUIRY:
            case UPDATE_ADVICE:
            case ADVICE:
                Call<Inquiry> call = retrofit.create(InquiryMapper.class).getInquiry(id);
                call.enqueue(new CustomCallback<Inquiry>(this, StorageConstant.STORAGE_KEY_INQUIRY_RESULT) {
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
                        timeTxt.setText(DateTimeUtils.toString(inquiry.getCreatedAt()));
                        descTxt.setText(inquiry.getContent());
                        viewRecordAdapter = new ViewRecordAdapter(InquiryActivity.this, inquiry);
                        recyclerView.setAdapter(viewRecordAdapter);
                    }
                }.handle(404, R.string.not_found_err, "yêu cầu"));
        }
    }

    private void getImages(String albumId) {
        Call<List<String>> call = retrofit.create(ImageMapper.class).getImages(albumId);
        call.enqueue(new CustomCallback<List<String>>(this, StorageConstant.STORAGE_KEY_INQUIRY_RESULT) {
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

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
