package com.example.abclinic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
