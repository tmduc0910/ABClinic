package com.example.abclinic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abclinic.asynctask.AsyncResponse;
import com.abclinic.asynctask.GetUserInfoTask;
import com.abclinic.asynctask.LoginTask;
import com.abclinic.callback.CustomCallback;
import com.abclinic.constant.Constant;
import com.abclinic.constant.HttpStatus;
import com.abclinic.constant.StorageConstant;
import com.abclinic.entity.Account;
import com.abclinic.entity.UserInfo;
import com.abclinic.exception.CustomRuntimeException;
import com.abclinic.retrofit.RetrofitClient;
import com.abclinic.retrofit.api.UserInfoMapper;
import com.abclinic.utils.services.MyFirebaseService;
import com.abclinic.utils.services.PermissionUtils;
import com.example.abclinic.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Response;


public class LoginActivity extends CustomActivity {
    public static final boolean USE_SECURITY = false;
    public static final String DEFAULT_UID = "91200dd6-920b-48b4-86bb-674169a72458";

    Button loginBtn;
    EditText usernameEdt, passwordEdt;
    TextView statusTxt, errorTxt;

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
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils permissionUtils = new PermissionUtils(LoginActivity.this::doLogin);
                permissionUtils.checkPermission(LoginActivity.this, PermissionUtils.PERM_INTERNET, "kết nối mạng", PermissionUtils.REQUEST_INTERNET);
            }
        });
    }

    private void doLogin() {
        String username = usernameEdt.getText().toString();
        String password = passwordEdt.getText().toString();
        Account account = new Account("pat01@mail.com", "123456");
        LoginTask task = new LoginTask(LoginActivity.this,
                StorageConstant.STORAGE_KEY_USER);
        task.setDelegate(new AsyncResponse<String>() {
            @Override
            public void processResponse(String res) {
                getUserInfo(res);
            }

            @Override
            public void handleException(CustomRuntimeException e) {
                if (e.getCode() == HttpStatus.NOT_FOUND)
                    errorTxt.setVisibility(View.VISIBLE);
                else
                    Toast.makeText(LoginActivity.this, "Lỗi máy chủ (500)", Toast.LENGTH_LONG).show();
            }
        });
        task.execute(account);
    }

    private void getUserInfo(String uid) {
        retrofit = RetrofitClient.getClient(uid);
        Call<UserInfo> call = retrofit.create(UserInfoMapper.class).getInfo();
        call.enqueue(new CustomCallback<UserInfo>(LoginActivity.this) {
            @Override
            protected void processResponse(Response<UserInfo> response) {
                UserInfo userInfo = response.body();
                new GetUserInfoTask(appDatabase, LoginActivity.this, StorageConstant.STORAGE_KEY_USER, null)
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