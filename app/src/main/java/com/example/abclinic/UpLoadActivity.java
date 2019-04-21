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
import java.util.Date;

public class UpLoadActivity extends AppCompatActivity {

    ImageView imageView;

    TextView showTime, showDateTxt;
    Button pickTime, pickDateBtn;
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    Button btnSubmit;
    private long pressback;

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
                    case R.id.notifi:
                        Intent intent_mess = new Intent(UpLoadActivity.this, NotificationActivity.class);
                        startActivity(intent_mess);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                    case R.id.history:
                        Intent intent_notifi = new Intent(UpLoadActivity.this, HistoryActivity.class);
                        startActivity(intent_notifi);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                    case R.id.profile:
                        Intent intent_acc = new Intent(UpLoadActivity.this, ProfileActivity.class);
                        startActivity(intent_acc);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                }
                return false;
            }
        });



        // open camera
        Button butCam = findViewById(R.id.butOpencam);
        imageView = findViewById(R.id.imageView);

        butCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOpencam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentOpencam, 0);
            }
        });

        //show date
        showDateTxt = findViewById(R.id.showDateText);
        pickDateBtn = findViewById(R.id.pickDateButton);

        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                c.setTime(new Date());
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(UpLoadActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        showDateTxt.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, year, month, day);

                dpd.show();

            }


        });


        //showTime
        showTime = findViewById(R.id.showTime);
        pickTime = findViewById(R.id.btnPickTime);

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                int currentHour = c.get(Calendar.HOUR_OF_DAY);
                int currentMinute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(UpLoadActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        showTime.setText(hourOfDay + ":" + minutes);
                    }
                }, currentHour, currentMinute, false);
                tpd.show();

            }
        });

        //submit
        btnSubmit = findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (v.getId())
                {
                    case R.id.submit:
                        Toast.makeText(UpLoadActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }});



    }

    //save picture
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get(("data"));
        imageView.setImageBitmap(bitmap);
    }

    //set back press
    @Override
    public void onBackPressed() {
        if (pressback +2000> System.currentTimeMillis()){
            moveTaskToBack(true);
            return;
        } else {
            Toast.makeText(this, "Nhấn thoát lại lần nữa", Toast.LENGTH_SHORT).show();
        }

        pressback = System.currentTimeMillis();

    }
}
