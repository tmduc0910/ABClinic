package com.example.abclinic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //bottomnavigationbar
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:
                        Intent intentMess = new Intent(MessageActivity.this, UploadActivity.class);
                        startActivity(intentMess);
                        break;
                    case R.id.mess:

                        break;
                    case R.id.notifi:
                        Intent intentAcc = new Intent(MessageActivity.this, NotificationActivity.class);
                        startActivity(intentAcc);
                        break;
                    case R.id.profile:
                        Intent intentHome = new Intent(MessageActivity.this, ProfileActivity.class);
                        startActivity(intentHome);
                        break;
                }
                return false;
            }
        });
    }
}