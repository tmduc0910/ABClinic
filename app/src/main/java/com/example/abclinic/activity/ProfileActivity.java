package com.example.abclinic.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

import com.abclinic.asynctask.UpdateUserInfoTask;
import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.StorageConstant;
import com.abclinic.dto.MediaDto;
import com.abclinic.dto.RequestUpdateInfoDto;
import com.abclinic.entity.UserInfo;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.AuthMapper;
import com.abclinic.retrofit.api.ImageMapper;
import com.abclinic.retrofit.api.UserInfoMapper;
import com.abclinic.room.entity.UserEntity;
import com.abclinic.utils.DateTimeUtils;
import com.abclinic.utils.FileUtils;
import com.abclinic.utils.services.MediaService;
import com.abclinic.utils.services.PermissionUtils;
import com.abclinic.utils.services.intent.job.GetScheduleJob;
import com.example.abclinic.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.Collections;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends CustomActivity implements PopupMenu.OnMenuItemClickListener {
    Button settingBtn, helpBtn, exitBtn, profileBtn, acceptBtn, cancelBtn;
    EditText phoneNumberEdt, emailEdt, addressEdt;
    TextView nameTxt, phoneNumberTxt, dobTxt, emailTxt, addressTxt, joinDateTxt, genderTxt;
    SimpleDraweeView avatarImg;

    private UserInfo userInfo;
    private MediaDto media;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_USER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatarImg = this.findViewById(R.id.avatar);
        nameTxt = this.findViewById(R.id.name);
        genderTxt = this.findViewById(R.id.gender);
        dobTxt = this.findViewById(R.id.dob);
        phoneNumberTxt = this.findViewById(R.id.phoneNumber);
        emailTxt = this.findViewById(R.id.email);
        addressTxt = this.findViewById(R.id.address);
        joinDateTxt = this.findViewById(R.id.joinDate);

        //settingBtn button
        settingBtn = this.findViewById(R.id.settings);

        //helpBtn button
        helpBtn = this.findViewById(R.id.helps);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(ProfileActivity.this)
                        .setTitleText("Thông tin liên hệ:")
                        .setContentText("Số điện thoại: 09658 235 458\nEmail: abc@gmail.com")
                        .show();
            }
        });

        //exitBtn button
        exitBtn = this.findViewById(R.id.exit);
        exitBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Bạn có chắn chắn muốn thoát không?");
                builder.setCancelable(true);
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> call = retrofit.create(AuthMapper.class).logout();
                        call.enqueue(new CustomCallback<Void>(ProfileActivity.this) {
                            @Override
                            protected void processResponse(Response<Void> response) {
                                AsyncTask.execute(() -> {
                                    UserEntity userEntity = appDatabase.getUserDao().getUser(userInfo.getId());
                                    userEntity.setLogon(false);
                                    appDatabase.getUserDao().addUser(userEntity);
                                });

                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                                intent.putExtra(Constant.IS_LOGOUT, true);
                                startActivityForResult(intent, CODE_LOGOUT);
                            }
                        });
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //new GetPatientInfoTask().execute();

        profileBtn = this.findViewById(R.id.editInfoButton);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);
                phoneNumberEdt = dialogView.findViewById(R.id.phoneNumberEdit);
                emailEdt = dialogView.findViewById(R.id.edtemail);
                addressEdt = dialogView.findViewById(R.id.addressEdit);

                phoneNumberEdt.setText(userInfo.getPhoneNumber());
                addressEdt.setText(userInfo.getAddress());
                emailEdt.setText(userInfo.getEmail());

                acceptBtn = dialogView.findViewById(R.id.acceptButton);
                cancelBtn = dialogView.findViewById(R.id.cancelButton);

                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone = phoneNumberEdt.getText().toString();
                        String email = emailEdt.getText().toString();
                        String address = addressEdt.getText().toString();

                        if (!phone.isEmpty() && !email.isEmpty() && !address.isEmpty()) {
//                            phoneNumberTxt.setText(phoneNumberEdt.getText().toString());
//                            emailTxt.setText(emailEdt.getText().toString());
//                            addressTxt.setText(addressEdt.getText().toString());

                            RequestUpdateInfoDto requestUpdateInfoDto = new RequestUpdateInfoDto(address, email, phone);
                            Call<UserInfo> call = retrofit.create(UserInfoMapper.class).changeInfo(requestUpdateInfoDto);
                            call.enqueue(new CustomCallback<UserInfo>(ProfileActivity.this) {
                                @Override
                                protected void processResponse(Response<UserInfo> response) {
                                    userInfo = response.body();
                                    updateInfo();
                                    new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Thành công!")
                                            .setContentText("Đã cập nhật thông tin!")
                                            .show();
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Đề nghị điền đầy đủ thông tin!")
                                    .show();
                        }
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        //bottomnavigationbar
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:
                        Intent homeIntent = new Intent(ProfileActivity.this, UpLoadActivity.class);
                        startActivity(homeIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.notifi:
                        Intent messIntent = new Intent(ProfileActivity.this, NotificationActivity.class);
                        startActivity(messIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.history:
                        Intent historyIntent = new Intent(ProfileActivity.this, HistoryActivity.class);
                        startActivity(historyIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.profile:
                        break;
                }
                return false;
            }
        });

        GetScheduleJob.enqueueWork(this, null);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(ProfileActivity.this, v);
        popup.setOnMenuItemClickListener(ProfileActivity.this);
        popup.inflate(R.menu.avatar_popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.view_ava:
                userInfo = storageService.getUserInfo();
                if (userInfo.getAvatar() != null) {
                    new ImageViewer.Builder<>(this, Collections.singletonList(userInfo.getAvatar()))
                            .show();
                } else Toast.makeText(this, "Bạn chưa có ảnh đại diện!!", Toast.LENGTH_LONG).show();
                break;
            case R.id.upload_ava_cam:
                permissionUtils = new PermissionUtils(this::startCamera);
                permissionUtils.checkPermission(this, PermissionUtils.PERM_CAMERA, "máy ảnh", PermissionUtils.REQUEST_CAMERA);
                break;
            case R.id.upload_ava_gallery:
                permissionUtils = new PermissionUtils(this::startGallery);
                permissionUtils.checkPermission(this, PermissionUtils.PERM_READ_EXT_STORAGE, "viết bộ nhớ", PermissionUtils.REQUEST_READ_EXT_STORAGE);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MultipartBody.Part file = null;

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    file = FileUtils.getPart(media.getFile());
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        file = FileUtils.getPart(FileUtils.getAbsolutePath(this, data.getData()));
                    }
                }
                break;
        }

        if (file != null) {
            String uid = storageService.getUid();
            Retrofit retrofit = RetrofitClient.getClient(uid);
            Call<String> call = retrofit.create(ImageMapper.class).uploadAvatar(file);
            call.enqueue(new CustomCallback<String>(this) {
                @Override
                protected void processResponse(Response<String> response) {
                    avatarImg.setImageURI(response.body());
                    userInfo.setAvatar(response.body());
                    saveInfo();
                    Toast.makeText(ProfileActivity.this, getResources().getText(R.string.confirm_ava), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.REQUEST_CAMERA || requestCode == PermissionUtils.REQUEST_READ_EXT_STORAGE) {
            permissionUtils.grantedPermission(this, grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfo = storageService.getUserInfo();
        updateInfo();

        //edit profile
        avatarImg.setOnClickListener(this::showPopup);
    }

    private void updateInfo() {
        avatarImg.setImageURI(userInfo.getAvatar());
        nameTxt.setText(userInfo.getName());
        genderTxt.setText(userInfo.getGenderString());
        phoneNumberTxt.setText(userInfo.getPhoneNumber());
        emailTxt.setText(userInfo.getEmail());
        dobTxt.setText(DateTimeUtils.toString(userInfo.getDateOfBirth()));
        addressTxt.setText(userInfo.getAddress());
        joinDateTxt.setText(String.format("Bắt đầu khám từ: %s", DateTimeUtils.toString(userInfo.getCreatedAt())));
    }

    private void startCamera() {
        media = MediaService.getCameraIntent(this);
        startActivityForResult(media.getIntent(), 1);
    }

    private void startGallery() {
        media = new MediaDto();
        media.setIntent(MediaService.getGalleryIntent("Chọn ảnh đại diện"));
        startActivityForResult(media.getIntent(), 2);
    }

    private void saveInfo() {
        new UpdateUserInfoTask(appDatabase, ProfileActivity.this, StorageConstant.STORAGE_KEY_USER, null)
                .execute(userInfo);
        storageService.saveCache(StorageConstant.KEY_USER, userInfo.toString());
        updateInfo();
    }
}


