package com.abclinic.test.hashing;

import com.abclinic.utils.services.HashService;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashService hasher = new HashService();
        Scanner scanner = new Scanner(System.in);
        byte[] hashedPassword = "".getBytes();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        SecureRandom random = hasher.setSecureRandom();
        try {
            hashedPassword = hasher.hashPassword(password);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        System.out.println("SecureRandom is: " + random);
        System.out.println("Hashed password is: " + new String(hashedPassword, StandardCharsets.UTF_8));
    }

}
