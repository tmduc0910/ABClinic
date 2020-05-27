package com.example.abclinic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abclinic.asynctask.AsyncResponse;
import com.abclinic.asynctask.LoginTask;
import com.abclinic.constant.HttpStatus;
import com.abclinic.constant.StorageConstant;
import com.abclinic.entity.Account;
import com.abclinic.exception.CustomRuntimeException;
import com.abclinic.utils.services.PermissionUtils;
import com.example.abclinic.R;


public class LoginActivity extends CustomActivity {
    public static final boolean USE_SECURITY = false;
    public static final String DEFAULT_UID = "fa8d0039-ba7b-4b52-8535-fcbdd62be085";

    Button loginBtn;
    EditText usernameEdt, passwordEdt;
    TextView statusTxt, errorTxt;

    @Override
    public String getKey() {
        return null;
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
        if (USE_SECURITY) {
            String username = usernameEdt.getText().toString();
            String password = passwordEdt.getText().toString();
            Account account = new Account(username, password);
            LoginTask task = new LoginTask(LoginActivity.this,
                    StorageConstant.STORAGE_KEY_USER);
            task.setDelegate(new AsyncResponse<String>() {
                @Override
                public void processResponse(String res) {
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
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
        } else {
            storageService.saveCache(StorageConstant.KEY_UID, DEFAULT_UID);
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        }
    }
}