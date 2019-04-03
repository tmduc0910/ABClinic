package com.example.abclinic;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class UpLoad extends AppCompatActivity {

    ImageView imageView;

    TextView showTime, showDate;
    Button pickTime, pickDate;
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load);

        //bottomnavigationbar
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:

                        break;
                    case R.id.mess:
                        Intent intent_mess = new Intent(UpLoad.this, Message.class);
                        startActivity(intent_mess);
                        break;
                    case R.id.notifi:
                        Intent intent_notifi = new Intent(UpLoad.this, Notification.class);
                        startActivity(intent_notifi);
                        break;
                    case R.id.profile:
                        Intent intent_acc = new Intent(UpLoad.this, Profile.class);
                        startActivity(intent_acc);
                        break;
                }
                return false;
            }
        });



        // open camera
        Button butCam = (Button)findViewById(R.id.butOpencam);
        imageView = (ImageView)findViewById(R.id.imageView);

        butCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOpencam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentOpencam, 0);
            }
        });

        //show date
        showDate = (TextView) findViewById(R.id.showDate);
        pickDate = (Button) findViewById(R.id.btnPickDate);

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(UpLoad.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        showDate.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, day, month, year);
                dpd.show();

            }


        });


        //showTime
        showTime = (TextView) findViewById(R.id.showTime);
        pickTime = (Button) findViewById(R.id.btnPickTime);

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                int currentHour = c.get(Calendar.HOUR_OF_DAY);
                int currentMinute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(UpLoad.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        showTime.setText(hourOfDay + ":" + minutes);
                    }
                }, currentHour, currentMinute, false);
                tpd.show();

            }
        });

        //submit
        btnSubmit = (Button) findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (v.getId())
                {
                    case R.id.submit:
                        Toast.makeText(UpLoad.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }});



    }


    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
}
