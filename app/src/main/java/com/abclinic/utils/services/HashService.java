package com.abclinic.utils.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashService {
    private SecureRandom random;
    private KeySpec spec;
    private SecretKeyFactory factory;

    public SecureRandom setSecureRandom() {
        random = new SecureRandom();
        return random;
    }

    public byte[] hashPassword(String passwordToHash) throws InvalidKeySpecException {
        if (random == null) {
            System.out.println("You have to generate a SecureRandom first!");
            return null;
        }
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        spec = new PBEKeySpec(passwordToHash.toCharArray(), salt, 65536, 128);
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return factory.generateSecret(spec).getEncoded();
    }
}
