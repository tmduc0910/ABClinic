package com.example.abclinic.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abclinic.asynctask.SaveInquiryCacheTask;
import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.HttpStatus;
import com.abclinic.constant.StorageConstant;
import com.abclinic.dto.InquiryCacheDto;
import com.abclinic.dto.MediaDto;
import com.abclinic.dto.RecyclerImageDto;
import com.abclinic.dto.RequestCreateInquiryDto;
import com.abclinic.dto.ResponseAlbumDto;
import com.abclinic.entity.Inquiry;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.ImageMapper;
import com.abclinic.retrofit.api.InquiryMapper;
import com.abclinic.utils.DateTimeUtils;
import com.abclinic.utils.FileUtils;
import com.abclinic.utils.services.LocalStorageService;
import com.abclinic.utils.services.MediaService;
import com.abclinic.utils.services.PermissionUtils;
import com.example.abclinic.R;
import com.example.abclinic.adapter.ViewImageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpLoadActivity extends CustomActivity implements PopupMenu.OnMenuItemClickListener {
    private static boolean isFirstRun = true;
    RecyclerView recyclerView;
    RadioGroup radioGroup;
    RadioButton specRadio, dietRadio;
    TextView showTimeTxt, showDateTxt, countTxt, confirmTxt;
    EditText descEdt;
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    Button pickTime, pickDateBtn, choosePicBtn, deletePicBtn, btnSubmit;
    LinearLayoutManager layoutManager;
    TimePicker timePicker;
    private ViewImageAdapter viewImageAdapter;
    private List<RecyclerImageDto> images;
    private String uri;
    private RecyclerImageDto dto;
    private ResponseAlbumDto album;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_INQUIRY;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load);

        storageService = new LocalStorageService(this, StorageConstant.STORAGE_KEY_INQUIRY);
        if (isFirstRun) {
            storageService.deleteInquiryCache();
            isFirstRun = false;
        }
        descEdt = findViewById(R.id.Description);
        radioGroup = findViewById(R.id.radiogroup);
        specRadio = findViewById(R.id.selectrecord);
        dietRadio = findViewById(R.id.selectmeal);
        recyclerView = findViewById(R.id.recycler_upload);
        images = new LinkedList<>();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        countTxt = findViewById(R.id.txt_count);
        countTxt.setText(getString(R.string.img_count, images.size()));
        confirmTxt = findViewById(R.id.txt_confirm);
        choosePicBtn = findViewById(R.id.choose_pic_btn);
        deletePicBtn = findViewById(R.id.delete_pic_btn);

        deletePicBtn.setOnClickListener((v) -> {
            if (!images.isEmpty()) {
                SweetAlertDialog dialog = new SweetAlertDialog(UpLoadActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Xác nhận")
                        .setContentText("Bạn có chắc chắn muốn xóa toàn bộ ảnh đã chọn không?");
                dialog.setConfirmButton("Có", d -> {
                    images.clear();
                    countTxt.setText(getString(R.string.img_count, 0));
                    viewImageAdapter.notifyDataSetChanged();
                    d.cancel();
                });
                dialog.setCancelButton("Không", SweetAlertDialog::cancel);
                dialog.show();
            }
        });

        //bottomnavigationbar
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:

                        break;
                    case R.id.notifi:
                        startActivity(new Intent(UpLoadActivity.this, NotificationActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                    case R.id.history:
                        startActivity(new Intent(UpLoadActivity.this, HistoryActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(UpLoadActivity.this, ProfileActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                }
                return false;
            }
        });

        // display image from camera capture
//        imageView = findViewById(R.id.imageView);

        //show date
        showDateTxt = findViewById(R.id.showDateText);
        pickDateBtn = findViewById(R.id.pickDateButton);
        timePicker = new TimePicker(this);

        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                c.setTime(new Date());
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(UpLoadActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        DecimalFormat format = new DecimalFormat(Constant.DOUBLE_DIGIT_FORMAT);
                        format.setRoundingMode(RoundingMode.DOWN);
                        showDateTxt.setText(String.format("%s/%s/%s",
                                format.format(Double.valueOf(mDay)),
                                format.format(Double.valueOf(mMonth)),
                                mYear));
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        //showTimeTxt
        showTimeTxt = findViewById(R.id.showTime);
        pickTime = findViewById(R.id.btnPickTime);

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                int currentHour = c.get(Calendar.HOUR_OF_DAY);
                int currentMinute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(UpLoadActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        DecimalFormat format = new DecimalFormat(Constant.DOUBLE_DIGIT_FORMAT);
                        format.setRoundingMode(RoundingMode.DOWN);
                        showTimeTxt.setText(String.format("%s:%s",
                                format.format(Double.valueOf(hourOfDay)),
                                format.format(Double.valueOf(minutes))));
                    }
                }, currentHour, currentMinute, false);
                tpd.show();
                tpd.onTimeChanged(timePicker, currentHour, currentMinute);
            }
        });

        //submit
        btnSubmit = findViewById(R.id.submit);
        btnSubmit.setOnClickListener((v) -> {
            try {
                if (getRadioBtnIndex() < 0 ||
                        descEdt.getText() == null ||
                        showDateTxt.getText().toString().equals("") ||
                        showTimeTxt.getText().toString().equals("")) {
                    confirmTxt.setText(R.string.confirm_inq_false);
                    confirmTxt.setVisibility(View.VISIBLE);
                } else {
                    String uid = storageService.getUid();
                    Retrofit retrofit = RetrofitClient.getClient(uid);
                    RequestCreateInquiryDto requestCreateInquiryDto = new RequestCreateInquiryDto(
                            getRadioBtnIndex(),
                            null,
                            descEdt.getText().toString(),
                            showDateTxt.getText().toString() + " " + showTimeTxt.getText().toString() + ":00"
                    );

                    if (!images.isEmpty()) {
                        MultipartBody.Part[] files = images.stream()
                                .map(i -> FileUtils.getPart(i.getPath()))
                                .collect(Collectors.toList())
                                .toArray(new MultipartBody.Part[0]);
                        Call<ResponseAlbumDto> call = retrofit.create(ImageMapper.class).uploadImages(files);
                        call.enqueue(new CustomCallback<ResponseAlbumDto>(UpLoadActivity.this) {
                            @Override
                            protected void processResponse(Response<ResponseAlbumDto> response) {
                                album = response.body();
                                requestCreateInquiryDto.setAlbumId(album.getAlbumId());
                                createInquiry(requestCreateInquiryDto);
                            }
                        });
                    } else createInquiry(requestCreateInquiryDto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createInquiry(RequestCreateInquiryDto requestCreateInquiryDto) {
        Call<Inquiry> createCall = retrofit.create(InquiryMapper.class).createInquiry(requestCreateInquiryDto);
        createCall.enqueue(new CustomCallback<Inquiry>(this) {
            @Override
            protected void processResponse(Response<Inquiry> response) {
                storageService.deleteInquiryCache();

                btnSubmit.setEnabled(false);
                confirmTxt.setText(R.string.confirm_inq_true);
                confirmTxt.setVisibility(View.VISIBLE);
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioGroup.getChildAt(i).setEnabled(false);
                }
                choosePicBtn.setEnabled(false);
                descEdt.setEnabled(false);
                pickDateBtn.setEnabled(false);
                showDateTxt.setEnabled(false);
                showTimeTxt.setEnabled(false);
                pickTime.setEnabled(false);
            }

            @Override
            protected void processException(Response<Inquiry> response) {
                if (response.code() == HttpStatus.FORBIDDEN) {
                    confirmTxt.setText(getMessage(response.code()));
                } else super.processException(response);
            }
        }.handle(HttpStatus.FORBIDDEN, R.string.inq_failed_queue_err)
                .handle(HttpStatus.BAD_REQUEST, R.string.inq_failed_wrong_type_err));
    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean f = showTimeTxt.requestFocus();
        if (btnSubmit.isEnabled()) {
            InquiryCacheDto dto = new InquiryCacheDto();
            dto.setType(getRadioBtnIndex());
            dto.setUris(images.stream().map(i -> i.getUri().toString()).collect(Collectors.toList()));
            dto.setContent(descEdt.getText().toString());

            if (!showDateTxt.getText().toString().equals(""))
                dto.setDate(showDateTxt.getText().toString());
            if (!showTimeTxt.getText().toString().equals(""))
                dto.setTime(showTimeTxt.getText().toString());
            new SaveInquiryCacheTask(this, StorageConstant.STORAGE_KEY_INQUIRY).execute(dto);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        InquiryCacheDto cacheDto = storageService.getInquiryCache();
        updateCache(cacheDto);
        dto = new RecyclerImageDto();
    }

    private void updateCache(InquiryCacheDto cacheDto) {
        if (cacheDto.getType() != -1) {
            radioGroup.check(radioGroup.getChildAt(cacheDto.getType()).getId());
        }
        if (images.isEmpty()) {
            images = cacheDto.getUris().stream().map(i -> new RecyclerImageDto(Uri.parse(i))).collect(Collectors.toList());
            showImages();
        }
        descEdt.setText(cacheDto.getContent());
        if (cacheDto.getDate() != null) {
            showDateTxt.setText(DateTimeUtils.toString(cacheDto.getDate()));
        }
        if (cacheDto.getTime() != null)
            showTimeTxt.setText(cacheDto.getTime());
    }

    //popup menu to upload image
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(UpLoadActivity.this, v);
        popup.setOnMenuItemClickListener(UpLoadActivity.this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_camera:
                permissionUtils = new PermissionUtils(this::startCamera);
                permissionUtils.checkPermission(this, PermissionUtils.PERM_CAMERA, "máy ảnh", PermissionUtils.REQUEST_CAMERA);
                break;
            case R.id.open_library:
                permissionUtils = new PermissionUtils(this::startGallery);
                permissionUtils.checkPermission(this, PermissionUtils.PERM_READ_EXT_STORAGE, "viết bộ nhớ", PermissionUtils.REQUEST_READ_EXT_STORAGE);
                break;
        }
        return false;
    }

    //save cameracapture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri uri = Uri.parse(this.uri);
                    dto.setUri(uri);
                    images.add(dto);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            dto = new RecyclerImageDto();
                            dto.setUri(imageUri);
                            dto.setPath(FileUtils.getAbsolutePath(this, imageUri));
                            images.add(dto);
                        }
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    } else if (data.getData() != null) {
                        String imagePath = FileUtils.getAbsolutePath(this, data.getData());
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        dto.setPath(imagePath);
                        dto.setUri(data.getData());
                        images.add(dto);
                    }
                    break;
                }
        }
        showImages();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.REQUEST_CAMERA || requestCode == PermissionUtils.REQUEST_READ_EXT_STORAGE) {
            permissionUtils.grantedPermission(this, grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void startCamera() {
        MediaDto media = MediaService.getCameraIntent(this);
        uri = media.getUri().toString();
        dto.setPath(media.getFile().getAbsolutePath());
        startActivityForResult(media.getIntent(), 0);
    }

    private void startGallery() {
        startActivityForResult(MediaService.getGalleryMultipleIntent("Chọn hình ảnh"), 1);
    }

    protected void showImages() {
        viewImageAdapter = new ViewImageAdapter(UpLoadActivity.this, images);
        recyclerView.setAdapter(viewImageAdapter);
        countTxt.setText(getString(R.string.img_count, images.size()));
    }

    private int getRadioBtnIndex() {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton btn = radioGroup.findViewById(radioButtonId);
        return radioGroup.indexOfChild(btn);
    }
}
