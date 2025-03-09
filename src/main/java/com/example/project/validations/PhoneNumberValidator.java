package com.example.project.validations;

import com.example.project.annotations.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber,String> {


    private int minLength;
    private int maxLength;

    @Override
    public void initialize(ValidPhoneNumber valid){
        this.minLength=valid.minLength();
        this.maxLength= valid.maxLength();
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context){
        if(phone==null || phone.isBlank())
            return false;

        if(phone.length()<minLength)
            return false;

        if(phone.length()>maxLength)
            return false;

        return phone.matches("^\\+?[1-9][0-9]{7,14}$");

    }
}
