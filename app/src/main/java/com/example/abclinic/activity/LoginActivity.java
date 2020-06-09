package com.example.abclinic.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abclinic.asynctask.AsyncResponse;
import com.abclinic.asynctask.LoginTask;
import com.abclinic.asynctask.UpdateUserInfoTask;
import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.HttpStatus;
import com.abclinic.constant.StorageConstant;
import com.abclinic.entity.Account;
import com.abclinic.entity.UserInfo;
import com.abclinic.exception.CustomRuntimeException;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.UserInfoMapper;
import com.abclinic.room.entity.UserEntity;
import com.abclinic.utils.services.MyFirebaseService;
import com.abclinic.utils.services.PermissionUtils;
import com.example.abclinic.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Response;


public class LoginActivity extends CustomActivity {
    public static final boolean USE_SECURITY = true;
    public static final String DEFAULT_UID = "91200dd6-920b-48b4-86bb-674169a72458";

    Button loginBtn;
    EditText usernameEdt, passwordEdt;
    TextView statusTxt, errorTxt;
    CheckBox rememberMeChk;

    @Override
    public String getKey() {
        return StorageConstant.STORAGE_KEY_USER;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginButton);
        usernameEdt = findViewById(R.id.usernameEdit);
        passwordEdt = findViewById(R.id.passwordEdit);
        statusTxt = findViewById(R.id.statusText);
        errorTxt = findViewById(R.id.error_txt);
        rememberMeChk = findViewById(R.id.remember_me);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            boolean isLogout = data.getBoolean(Constant.IS_LOGOUT);
            if (isLogout) {
                MyFirebaseService.subject.detachAll();
                Snackbar.make(loginBtn, "Đăng xuất thành công", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light, null))
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncTask.execute(() -> {
            UserEntity accountLogon = appDatabase.getUserDao().getLogonUser();
            if (accountLogon != null) {
                rememberMeChk.setChecked(true);
                doLogin(accountLogon.getEmail(), accountLogon.getPassword());
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils permissionUtils = new PermissionUtils(() -> {
                    doLogin(usernameEdt.getText().toString(), passwordEdt.getText().toString());
                });
                permissionUtils.checkPermission(LoginActivity.this, PermissionUtils.PERM_INTERNET, "kết nối mạng", PermissionUtils.REQUEST_INTERNET);
            }
        });
    }

    private void doLogin(String username, String password) {
        if (!USE_SECURITY) {
            username = "pat01@mail.com";
            password = "123456";
        }
        Account account = new Account(username, password);
        LoginTask task = new LoginTask(LoginActivity.this,
                StorageConstant.STORAGE_KEY_USER);

        String[] pws = new String[1];
        pws[0] = password;
        task.setDelegate(new AsyncResponse<String>() {
            @Override
            public void processResponse(String res) {
                getUserInfo(res, pws[0]);
//                LocalDateTime now = LocalDateTime.now();
//                getInquiries(now.getMonthValue(), now.getYear());
            }

            @Override
            public void handleException(CustomRuntimeException e) {
                if (e.getCode() == HttpStatus.NOT_FOUND)
                    errorTxt.setVisibility(View.VISIBLE);
                else {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Lỗi máy chủ (500)", Toast.LENGTH_LONG).show();
                }
            }
        });
        task.execute(account);
    }

    private void getUserInfo(String uid, String password) {
        retrofit = RetrofitClient.getClient(uid);
        Call<UserInfo> call = retrofit.create(UserInfoMapper.class).getInfo();
        call.enqueue(new CustomCallback<UserInfo>(LoginActivity.this) {
            @Override
            protected void processResponse(Response<UserInfo> response) {
                UserInfo userInfo = response.body();
                userInfo.setPassword(password);
                if (rememberMeChk.isChecked())
                    userInfo.setLogon(true);

                new UpdateUserInfoTask(appDatabase, LoginActivity.this, StorageConstant.STORAGE_KEY_USER, null)
                        .execute(userInfo);
                storageService.saveCache(StorageConstant.KEY_USER, userInfo.toString());

                FirebaseMessaging.getInstance().subscribeToTopic("users-" + userInfo.getId())
                        .addOnCompleteListener((task) -> {
                            Log.d(Constant.DEBUG_TAG, "Subscribe to topic users-" + userInfo.getId());
                        });
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
            }
        });
    }
}