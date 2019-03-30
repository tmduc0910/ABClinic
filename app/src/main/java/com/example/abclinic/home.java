package com.example.abclinic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                    new home_fragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home1:
                            selectedFragment = new home_fragment();
                            break;
                        case R.id.mess:
                            selectedFragment = new mess_fragment();
                            break;
                        case R.id.notifi:
                            selectedFragment = new notifi_fragment();
                            break;
                        case R.id.invita:
                            selectedFragment = new invita_fragment();
                            break;
                        case R.id.acc:
                            selectedFragment = new acc_fragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                            selectedFragment).commit();

                    return true;
                }
            };

}
