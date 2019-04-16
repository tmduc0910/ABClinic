package com.example.abclinic;

import android.content.Intent;
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

import com.example.abclinic.HistoryActivity;
import com.example.abclinic.MainActivity;
import com.example.abclinic.NotificationActivity;
import com.example.abclinic.R;
import com.example.abclinic.UpLoadActivity;

public class ProfileActivity extends AppCompatActivity {

    Button setting, help, exit, edtprofile, accept, cancel;
    EditText edtphonenumber,edtemail, edtaddress;
    TextView  phonenumber,email, address;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_help, null);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //exit button
        exit = (Button) this.findViewById(R.id.exit);
        exit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent_exit = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent_exit);
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
                            Toast.makeText(ProfileActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Đề nghị điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
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
                        break;
                    case R.id.notifi:
                        Intent intent_mess = new Intent(ProfileActivity.this, NotificationActivity.class);
                        startActivity(intent_mess);
                        break;
                    case R.id.history:
                        Intent intent_acc = new Intent(ProfileActivity.this, HistoryActivity.class);
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
