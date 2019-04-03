<<<<<<< HEAD
package com.example.abclinic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Message extends AppCompatActivity {

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
                        Intent intent_mess = new Intent(Message.this, UpLoad.class);
                        startActivity(intent_mess);
                        break;
                    case R.id.mess:

                        break;
                    case R.id.notifi:
                        Intent intent_acc = new Intent(Message.this, Notification.class);
                        startActivity(intent_acc);
                        break;
                    case R.id.profile:
                        Intent intent_home = new Intent(Message.this, Profile.class);
                        startActivity(intent_home);
                        break;
                }
                return false;
            }
        });
    }
}
=======
package com.example.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Message extends AppCompatActivity {

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
                        Intent intent_mess = new Intent(Message.this, UpLoad.class);
                        startActivity(intent_mess);
                        break;
                    case R.id.mess:

                        break;
                    case R.id.notifi:
                        Intent intent_acc = new Intent(Message.this, Notification.class);
                        startActivity(intent_acc);
                        break;
                    case R.id.profile:
                        Intent intent_home = new Intent(Message.this, Profile.class);
                        startActivity(intent_home);
                        break;
                }
                return false;
            }
        });
    }
}
>>>>>>> origin/master
