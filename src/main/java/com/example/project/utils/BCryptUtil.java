package com.example.project.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptUtil {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public String hashPassword(String password){
            return encoder.encode(password);
    }

    public boolean verifyPassword(String password,String hashedPassword){
         return encoder.matches(password,hashedPassword);
    }
}
