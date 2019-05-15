package com.example.abclinic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abclinic.entity.Patient;
import com.abclinic.utils.services.JsonJavaConvertingService;
import com.abclinic.utils.services.RequestHandlingService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {
    Button loginBtn;
    EditText emailEdt, passwordEdt, urlEdt;
    TextView statusTxt;
    private long pressBack;
    private final String TAG = "DEBUG LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginButton);
        emailEdt = findViewById(R.id.emailEdit);
        passwordEdt = findViewById(R.id.passwordEdit);
        urlEdt = findViewById(R.id.urlEdit);
        statusTxt = findViewById(R.id.statusText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, UpLoadActivity.class));
  /*              String email = emailEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String url = urlEdt.getText().toString();

                String postParam = "{\n" +
                        "    \"email\": \"" + email.trim() + "\",\n" +
                        "    \"password\": \"" + password.trim() + "\"\n" +
                        "}";
                //Log.d(TAG, postParam);
                new PostJSONTask().execute(postParam, "http://" + url + ":3000/auth/login");

            */}
        });
    }


/*    private class PostJSONTask extends AsyncTask<String, Void, String> {
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
                return requestHandler.postRequest(strings[0].trim(), strings[1]);
            } catch (IOException e) {
                return "Unable to retrieve data";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result != null) {
                Log.d(TAG, result);
                Patient patient;
                try {
                    patient = (Patient) converter.mapJsonToObjectsWithStatus(result, Patient.class).get(1);

                    saveUserData(patient);

                    Intent uploadIntent = new Intent(MainActivity.this, UpLoadActivity.class);
                    startActivity(uploadIntent);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                //statusTxt.setText(account.toString());


                //Log.d(TAG, result);
            } else {
                statusTxt.setText("FAILED! USERNAME OR PASSWORD NOT MATCHED!\n");
                statusTxt.setTextColor(Color.RED);
                //Log.d(TAG, "NULL");
            }
        }

        private void saveUserData(Patient patient) {
            SharedPreferences sharedPreferences = getSharedPreferences("userAccount", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            //TODO: tự động lấy các attribute và gán vào editor?

            editor.putInt("Id", patient.getId());
            editor.putString("Uid", patient.getUid());
            editor.putString("Email", patient.getEmail());
            editor.putString("Password", patient.getPassword());
            editor.putString("Name", patient.getName());
            editor.putInt("Gender", patient.getGender());
            editor.putString("Birthday", patient.getBirthday());
            editor.putString("Phone", patient.getPhone());
            editor.putString("Address", patient.getAddress());
            editor.putString("JoinDate", patient.getJoinDate());
            editor.putString("CreatedAt", patient.getCreatedDate());

            editor.commit();
            Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
        }
    } */

    @Override
    public void onBackPressed() {
        if (pressBack +2000> System.currentTimeMillis()){
            moveTaskToBack(true);
            return;
        } else {
            Toast.makeText(this, "Nhấn thoát lại lần nữa", Toast.LENGTH_SHORT).show();
        }

        pressBack = System.currentTimeMillis();

    }
}