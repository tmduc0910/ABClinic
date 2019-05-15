package com.example.abclinic;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpLoadActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    Uri imageUri;
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
                        startActivity(new Intent(UpLoadActivity.this, NotificationActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                    case R.id.history:
                        startActivity(new Intent(UpLoadActivity.this, HistoryActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(UpLoadActivity.this, ProfileActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_feft);
                        break;
                }
                return false;
            }
        });

        // display image from camera capture
        imageView = findViewById(R.id.imageView);

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
                        new SweetAlertDialog(UpLoadActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Thành công!")
                                .show();
                        break;
                }

            }});
    }

    //popup menu to upload image
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(UpLoadActivity.this, v);
        popup.setOnMenuItemClickListener(UpLoadActivity.this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_camera:
                startActivityForResult(new Intent( MediaStore.ACTION_IMAGE_CAPTURE), 0);
                break;
            case R.id.open_library:
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);
                break;
        }
        return false;
    }

    //save cameracapture
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 0:
                Bitmap bitmap = (Bitmap) data.getExtras().get(("data"));
                imageView.setImageBitmap(bitmap);
                break;
            case 1:
                if (resultCode == RESULT_OK ){
                    imageUri = data.getData();
                    imageView.setImageURI(imageUri);
                }
                break;
        }
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
