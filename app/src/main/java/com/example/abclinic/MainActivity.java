package com.example.abclinic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abclinic.test.login.Account;
import com.abclinic.utils.services.JsonJavaConvertingService;
import com.abclinic.utils.services.RequestHandlingService;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    EditText usernameEdt, passwordEdt, urlEdt;
    TextView statusTxt;
    private long pressback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginButton);
        usernameEdt = findViewById(R.id.usernameEdit);
        passwordEdt = findViewById(R.id.passwordEdit);
        urlEdt = findViewById(R.id.urlEdit);
        statusTxt = findViewById(R.id.statusText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent open = new Intent(MainActivity.this, UpLoadActivity.class);
               startActivity(open);
                /*
                String username = usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String url = urlEdt.getText().toString();

                String postParam = "{\n" +
                        "    \"username\": \"" + username + "\",\n" +
                        "    \"password\": \"" + password + "\"\n" +
                        "}";
                new PostJSONTask().execute(postParam, "http://" + url + ":3000/auth/login");
                */
            }
        });
    }

/*
    private class PostJSONTask extends AsyncTask<String, Void, String> {
        private final String TAG = "DEBUG LOG";
        private RequestHandlingService requestHandler = new RequestHandlingService();
        private JsonJavaConvertingService converter = new JsonJavaConvertingService();
        private SweetAlertDialog progressDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            progressDialog.setTitleText("Loading")
                    .setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //Log.d(TAG, "EXECUTING");
                return requestHandler.postRequest(strings[0], strings[1]);
            } catch (IOException e) {
                return "Unable to retrieve data";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result != null) {
                Account account;
                account = (Account) converter.mapJsonToObject(result, Account.class);
                //statusTxt.setText(account.toString());

                saveUserData(account);

                Intent uploadIntent = new Intent(MainActivity.this, UpLoadActivity.class);
                startActivity(uploadIntent);
                //Log.d(TAG, result);
            } else {
                statusTxt.setText("FAILED! USERNAME OR PASSWORD NOT MATCHED!\n");
                statusTxt.setTextColor(Color.RED);
                //Log.d(TAG, "NULL");
            }
        }

        private void saveUserData(Account account) {
            SharedPreferences sharedPreferences = getSharedPreferences("userAccount", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("Username", account.getUsername());
            editor.putString("Password", account.getPassword());
            editor.putInt("Id", account.getId());
            editor.putString("Name", account.getName());
            editor.putString("Gender", account.getGender());
            editor.putString("Email", account.getEmail());
            editor.putString("Phone", account.getPhone());

            editor.commit();
            Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
        }
    }
*/

    @Override
    public void onBackPressed() {
        if (pressback +2000> System.currentTimeMillis()){
            moveTaskToBack(true);
            return;
        } else {
            Toast.makeText(this, "Nhấn thoát lại lần nữa", Toast.LENGTH_SHORT).show();
        }

        pressback = System.currentTimeMillis();

    }
}