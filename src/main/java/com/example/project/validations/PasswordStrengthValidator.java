package com.example.project.validations;

import com.example.project.annotations.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {

    private int minLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecialchar;

    public static final List<String> weakPasswords = Arrays.asList(
            "12345", "1234", "123456", "1234567", "12345678", "123456789", "1234567890",
            "password", "password1", "password123", "pass", "pass123", "passwd", "admin",
            "qwerty", "qwerty123", "asdf", "asdfgh", "letmein", "welcome", "abc123",
            "iloveyou", "monkey", "football", "baseball", "dragon", "sunshine",
            "123qwe", "qwe123", "1q2w3e4r", "qwertyuiop", "111111", "000000", "123321",
            "222222","3333333","444444","555555","666666","777777","888888","999999",
            "654321", "7777777", "987654321", "zxcvbnm", "qazwsx", "password!", "test",
            "hello", "trustno1", "user", "root", "temp", "guest", "admin123", "passw0rd",
            "123abc", "123123", "abcabc", "qwert", "passw0rd", "letmein123", "welcome123",
            "secret", "superman", "batman", "master", "login", "default", "access", "love",
            "flower", "star", "freedom", "summer", "winter", "autumn", "spring"
    );

    @Override
    public void initialize(PasswordValidator passwordValidator) {
        this.minLength = passwordValidator.minLength();
        this.requireUppercase = passwordValidator.requireUppercase();
        this.requireLowercase = passwordValidator.requireLowercase();
        this.requireDigit = passwordValidator.requireDigit();
        this.requireSpecialchar = passwordValidator.requireSpecialChar();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isBlank()) {
            return buildValidationResponse(context, "Password cannot be empty.");
        }

        if (weakPasswords.contains(password.toLowerCase())) {
            return buildValidationResponse(context, "Password is too weak. Choose a stronger password.");
        }

        if (password.length() < minLength) {
            return buildValidationResponse(context, "Password must be at least " + minLength + " characters long.");
        }

        if (requireUppercase && !Pattern.compile("[A-Z]").matcher(password).find()) {
            return buildValidationResponse(context, "Password must contain at least one uppercase letter.");
        }

        if (requireLowercase && !Pattern.compile("[a-z]").matcher(password).find()) {
            return buildValidationResponse(context, "Password must contain at least one lowercase letter.");
        }

        if (requireDigit && !Pattern.compile("\\d").matcher(password).find()) {
            return buildValidationResponse(context, "Password must contain at least one digit.");
        }

        if (requireSpecialchar && !Pattern.compile("[@#$%^&*!]").matcher(password).find()) {
            return buildValidationResponse(context, "Password must contain at least one special character (@#$%^&*! ).");
        }

        return true;
    }

    private boolean buildValidationResponse(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
