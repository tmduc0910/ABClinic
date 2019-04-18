package com.example.abclinic;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abclinic.HistoryActivity;
import com.example.abclinic.MainActivity;
import com.example.abclinic.NotificationActivity;
import com.example.abclinic.R;
import com.example.abclinic.UpLoadActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileActivity extends AppCompatActivity {

    Button setting, help, exit, edtprofile, accept, cancel;
    EditText edtphonenumber,edtemail, edtaddress;
    TextView  phonenumber,email, address;
    private long pressback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setting button
        setting = (Button) this.findViewById(R.id.settings);

        //help button
        help = (Button) this.findViewById(R.id.helps);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(ProfileActivity.this)
                        .setTitleText("Thôn tin liên hệ:")
                        .setContentText("Số điện thoại: 09658 235 458\nEmail: abc@gmail.com")
                        .show();
            }
        });

        //exit button
        exit = (Button) this.findViewById(R.id.exit);
        exit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setMessage("Bạn có chắn chắn muốn thoát không?");
                builder.setCancelable(true);
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProfileActivity.super.startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    }
                });

                AlertDialog  alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //edit profile
        phonenumber = (TextView) this.findViewById(R.id.phonenumber);
        email = (TextView) this.findViewById(R.id.email);
        address = (TextView) this.findViewById(R.id.address);

        edtprofile = (Button) this.findViewById(R.id.show_edtbox);
        edtprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View viewdialog = getLayoutInflater().inflate(R.layout.dialog_editprofile, null);
                edtphonenumber = (EditText) viewdialog.findViewById(R.id.edtphonenumber);
                edtemail = (EditText) viewdialog.findViewById(R.id.edtemail);
                edtaddress = (EditText) viewdialog.findViewById(R.id.edtaddress);

                accept = (Button) viewdialog.findViewById(R.id.accept_edit);
                cancel = (Button) viewdialog.findViewById(R.id.cancel_edit);

                builder.setView(viewdialog);
                final  AlertDialog dialog =builder.create();
                dialog.show();

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!edtphonenumber.getText().toString().isEmpty() && !edtemail.getText().toString().isEmpty()
                                && !edtaddress.getText().toString().isEmpty()){
                            phonenumber.setText(edtphonenumber.getText().toString());
                            email.setText(edtemail.getText().toString());
                            address.setText(edtaddress.getText().toString());

                            new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Thành công!")
                                    .show();
                            dialog.dismiss();

                        } else {
                            new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Đề nghị điền đầy đủ thông tin!")
                                    .show();
                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

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
                        Intent intent_home = new Intent(ProfileActivity.this, UpLoadActivity.class);
                        startActivity(intent_home);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.notifi:
                        Intent intent_mess = new Intent(ProfileActivity.this, NotificationActivity.class);
                        startActivity(intent_mess);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.history:
                        Intent intent_acc = new Intent(ProfileActivity.this, HistoryActivity.class);
                        startActivity(intent_acc);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.profile:

                        break;
                }
                return false;
            }
        });

    }

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
