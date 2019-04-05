package com.example.abclinic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView usernameText, passwordText, idText, nameText, genderText, emailText, phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameText = findViewById(R.id.usernameTxt);
        passwordText = findViewById(R.id.passwordTxt);
        idText = findViewById(R.id.idTxt);
        nameText = findViewById(R.id.nameTxt);
        genderText = findViewById(R.id.genderTxt);
        emailText = findViewById(R.id.emailTxt);
        phoneText = findViewById(R.id.phoneTxt);

        SharedPreferences sharedPreferences = getSharedPreferences("userAccount", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String username = sharedPreferences.getString("Username", "");
            String password = sharedPreferences.getString("Password", "");
            int id = sharedPreferences.getInt("Id", 0);
            String name = sharedPreferences.getString("Name", "");
            String gender = sharedPreferences.getString("Gender", "");
            String email = sharedPreferences.getString("Email", "");
            String phone = sharedPreferences.getString("Phone", "");

            usernameText.setHint(username);
            passwordText.setHint(password);
            idText.setHint(Integer.toString(id));
            nameText.setHint(name);
            genderText.setHint(gender);
            emailText.setHint(email);
            phoneText.setHint(phone);
        }

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
                        Intent intent_home = new Intent(Profile.this, UpLoad.class);
                        startActivity(intent_home);
                        break;
                    case R.id.mess:
                        Intent intent_mess = new Intent(Profile.this, Message.class);
                        startActivity(intent_mess);
                        break;
                    case R.id.notifi:
                        Intent intent_acc = new Intent(Profile.this, Notification.class);
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
