package com.almousleck.security;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class Encoder {

    public String encode(CharSequence value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedValue = digest.digest(value.toString().getBytes());
            return Base64.getEncoder().encodeToString(encodedValue);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error encoding password: ", ex);
        }
    }

    public boolean matches(CharSequence password, String encodedValue) {
        return encode(password).equals(encodedValue);
    }
}
