package com.example.abclinic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abclinic.R;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 1000; //1 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent mySuperIntent = new Intent(MainActivity.this, LoginActivity.class);
            if (getIntent().getExtras() != null)
                mySuperIntent.putExtras(getIntent());
            startActivity(mySuperIntent);
            finish();
        }, SPLASH_TIME);

    }
}
