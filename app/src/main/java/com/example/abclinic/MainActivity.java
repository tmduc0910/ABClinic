package com.example.abclinic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abclinic.test.login.Account;
import com.abclinic.utils.services.JsonJavaConvertingService;
import com.abclinic.utils.services.RequestHandlingService;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button bLogin;
    EditText tUsername, tPassword;

    RequestHandlingService requestHandler = new RequestHandlingService();
    JsonJavaConvertingService converter = new JsonJavaConvertingService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = findViewById(R.id.loginButton);
        tUsername = findViewById(R.id.usernameText);
        tPassword = findViewById(R.id.passwordText);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tUsername.getText().toString();
                String password = tPassword.getText().toString();

                try {
                    String postParam = "{\n" +
                            "    \"username\": \"" + username + "\",\n" +
                            "    \"password\": \"" + password + "\"\n" +
                            "}";
                    String postJSON = requestHandler.postRequest(postParam, "http://localhost:3000/auth/login");

                    if (postJSON != null) {
                        Account account;
                        account = (Account) converter.mapJsonToObject(postJSON, Account.class);
                        System.out.println("\nSUCCESS\n" + account);
                    } else System.out.println("FAILED! USERNAME OR PASSWORD NOT MATCHED!\n");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}