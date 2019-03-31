package com.example.abclinic;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abclinic.test.login.Account;
import com.abclinic.utils.services.JsonJavaConvertingService;
import com.abclinic.utils.services.RequestHandlingService;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    EditText usernameEdt, passwordEdt;
    TextView statusTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginButton);
        usernameEdt = findViewById(R.id.usernameText);
        passwordEdt = findViewById(R.id.passwordText);
        statusTxt = findViewById(R.id.statusText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                String postParam = "{\n" +
                        "    \"username\": \"" + username + "\",\n" +
                        "    \"password\": \"" + password + "\"\n" +
                        "}";
                new PostJSONTask().execute(postParam, "http://192.168.1.136:3000/auth/login");
            }
        });
    }

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
                statusTxt.setText(account.toString());
                //Log.d(TAG, result);
            } else {
                statusTxt.setText("FAILED! USERNAME OR PASSWORD NOT MATCHED!\n");
                //Log.d(TAG, "NULL");
            }
        }
    }
}