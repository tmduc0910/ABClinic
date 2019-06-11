package com.example.abclinic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.abclinic.enums.Gender;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileActivity extends AppCompatActivity {

    Button settingBtn, helpBtn, exitBtn, profileBtn, acceptBtn, cancelBtn;
    EditText phoneNumberEdt, emailEdt, addressEdt;
    TextView nameTxt, phoneNumberTxt, emailTxt, addressTxt, joinDateTxt, genderTxt;
    private long pressBack;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //settingBtn button
        settingBtn = (Button) this.findViewById(R.id.settings);

        //helpBtn button
        helpBtn = (Button) this.findViewById(R.id.helps);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(ProfileActivity.this)
                        .setTitleText("Thông tin liên hệ:")
                        .setContentText("Số điện thoại: 09658 235 458\nEmail: abc@gmail.com")
                        .show();
            }
        });

        //exitBtn button
        exitBtn = (Button) this.findViewById(R.id.exit);
        exitBtn.setOnClickListener(new Button.OnClickListener() {
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
                        ProfileActivity.super.startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    }
                });

                AlertDialog  alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //edit profile
        nameTxt = this.findViewById(R.id.name);
        genderTxt = this.findViewById(R.id.gender);
        phoneNumberTxt = (TextView) this.findViewById(R.id.phoneNumber);
        emailTxt = (TextView) this.findViewById(R.id.email);
        addressTxt = (TextView) this.findViewById(R.id.address);
        joinDateTxt = this.findViewById(R.id.joinDate);

 /*       sharedPreferences = getSharedPreferences("userAccount", Context.MODE_PRIVATE);
        String nameValue = sharedPreferences.getString("Name", "");
        String genderValue = Gender.toString(Gender.toGender(sharedPreferences.getInt("Gender", 0)));
        String phoneValue = sharedPreferences.getString("Phone", "");
        String emailValue = sharedPreferences.getString("Email", "");
        String addressValue = sharedPreferences.getString("Address", "");
        String joinDateValue = sharedPreferences.getString("JoinDate", "");

        nameTxt.setText(nameValue);
        genderTxt.setText(genderValue);
        phoneNumberTxt.setText(phoneValue);
        emailTxt.setText(emailValue);
        addressTxt.setText(addressValue);
        joinDateTxt.setText("Khám từ ngày: " + joinDateValue);
*/
        profileBtn = (Button) this.findViewById(R.id.editInfoButton);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);
                phoneNumberEdt = (EditText) dialogView.findViewById(R.id.phoneNumberEdit);
                emailEdt = (EditText) dialogView.findViewById(R.id.edtemail);
                addressEdt = (EditText) dialogView.findViewById(R.id.addressEdit);

                acceptBtn = (Button) dialogView.findViewById(R.id.acceptButton);
                cancelBtn = (Button) dialogView.findViewById(R.id.cancelButton);

                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!phoneNumberEdt.getText().toString().isEmpty() && !emailEdt.getText().toString().isEmpty()
                                && !addressEdt.getText().toString().isEmpty()){
                            phoneNumberTxt.setText(phoneNumberEdt.getText().toString());
                            emailTxt.setText(emailEdt.getText().toString());
                            addressTxt.setText(addressEdt.getText().toString());

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

                cancelBtn.setOnClickListener(new View.OnClickListener() {
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
                        Intent homeIntent = new Intent(ProfileActivity.this, UpLoadActivity.class);
                        startActivity(homeIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.notifi:
                        Intent messIntent = new Intent(ProfileActivity.this, NotificationActivity.class);
                        startActivity(messIntent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.history:
                        Intent historyIntent = new Intent(ProfileActivity.this, HistoryActivity.class);
                        startActivity(historyIntent);
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
        if (pressBack + 2000 > System.currentTimeMillis()){
            moveTaskToBack(true);
            return;
        } else {
            Toast.makeText(this, "Nhấn thoát lại lần nữa", Toast.LENGTH_SHORT).show();
        }

        pressBack = System.currentTimeMillis();

    }
}
