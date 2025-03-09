package com.example.project.annotations;


import com.example.project.validations.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {

    String message() default "Invalid Phone number,please enter a valid phone number";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};

    int minLength() default 10;
    int maxLength() default 10;
}
