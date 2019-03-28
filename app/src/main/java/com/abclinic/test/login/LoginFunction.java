package com.abclinic.test.login;

import com.abclinic.utils.services.JsonJavaConvertingService;
import com.abclinic.utils.services.RequestHandlingService;

import java.io.IOException;
import java.util.Scanner;

public class LoginFunction {
    public static void main(String[] args) {
        RequestHandlingService requestHandler = new RequestHandlingService();
        JsonJavaConvertingService converter = new JsonJavaConvertingService();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

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
                    break;
                } else System.out.println("FAILED! USERNAME OR PASSWORD NOT MATCHED!\n");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
