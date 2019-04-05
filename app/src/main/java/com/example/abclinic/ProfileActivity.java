package com.example.abclinic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtId, edtName, edtGender, edtEmail, edtPhone;
    ImageButton imgToggle;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edtUsername = findViewById(R.id.usernameTxt);
        edtPassword = findViewById(R.id.passwordTxt);
        edtId = findViewById(R.id.idTxt);
        edtName = findViewById(R.id.nameTxt);
        edtGender = findViewById(R.id.genderTxt);
        edtEmail = findViewById(R.id.emailTxt);
        edtPhone = findViewById(R.id.phoneTxt);
        btnSubmit = findViewById(R.id.submitBtn);
        imgToggle = findViewById(R.id.toggleImg);

        final SharedPreferences sharedPreferences = getSharedPreferences("userAccount", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String username = sharedPreferences.getString("Username", "");
            String password = sharedPreferences.getString("Password", "");
            int id = sharedPreferences.getInt("Id", 0);
            String name = sharedPreferences.getString("Name", "");
            String gender = sharedPreferences.getString("Gender", "");
            String email = sharedPreferences.getString("Email", "");
            String phone = sharedPreferences.getString("Phone", "");

            edtUsername.setHint(username);
            edtPassword.setText(password);
            edtId.setHint(Integer.toString(id));
            edtName.setHint(name);
            edtGender.setHint(gender);
            edtEmail.setHint(email);
            edtPhone.setHint(phone);
        }

        imgToggle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        edtPassword.setTransformationMethod(null);
                        imgToggle.setColorFilter(Color.RED);
                        return true;
                    case MotionEvent.ACTION_UP:
                        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                        imgToggle.setColorFilter(Color.BLACK);
                        return true;
                }
                return false;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (!edtEmail.getText().toString().equals(""))
                    editor.putString("Email", edtEmail.getText().toString());
                if (!edtPhone.getText().toString().equals(""))
                    editor.putString("Phone", edtPhone.getText().toString());
                editor.commit();

                Toast.makeText(ProfileActivity.this, "Đã lưu!", Toast.LENGTH_SHORT).show();

                finish();
                startActivity(getIntent());
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
                        Intent intent_home = new Intent(ProfileActivity.this, UploadActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.mess:
                        Intent intent_mess = new Intent(ProfileActivity.this, MessageActivity.class);
                        startActivity(intent_mess);
                        break;
                    case R.id.notifi:
                        Intent intent_acc = new Intent(ProfileActivity.this, NotificationActivity.class);
                        startActivity(intent_acc);
                        break;
                    case R.id.profile:

                        break;
                }
                return false;
            }
        });

    }
}
