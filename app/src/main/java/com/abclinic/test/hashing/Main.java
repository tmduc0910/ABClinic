package com.abclinic.test.hashing;

import com.abclinic.utils.services.HashService;

import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashService hasher = new HashService();
        Scanner scanner = new Scanner(System.in);
        byte[] hashedPassword = "".getBytes();

        while (true) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            byte[] random = hasher.getSecureRandom();
            try {
                hashedPassword = hasher.hashPassword(password);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

            System.out.println("SecureRandom is: " + hasher.bytesToString(random));
            System.out.println("Hashed password is: " + hasher.bytesToString(hashedPassword));
        }
    }

}
