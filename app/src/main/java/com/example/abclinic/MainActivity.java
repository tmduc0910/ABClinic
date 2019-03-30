package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button but_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_login = (Button) this.findViewById(R.id.button_login);

        but_login.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                    Intent intent_home = new Intent(MainActivity.this, home.class);
                    startActivity(intent_home);

            }});

    }
}
