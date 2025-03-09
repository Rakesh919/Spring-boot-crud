package com.example.project.validations;

import com.example.project.annotations.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;


@Slf4j
public class FieldMatcherValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstField;
    private String secondField;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstField = constraintAnnotation.first();
        this.secondField = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field first = value.getClass().getDeclaredField(firstField);
            Field second = value.getClass().getDeclaredField(secondField);

            first.setAccessible(true);
            second.setAccessible(true);

            Object firstValue = first.get(value);
            Object secondValue = second.get(value);

            return firstValue != null && firstValue.equals(secondValue);
        } catch (Exception e) {
          log.info("Exception occured ",e);
            return false;
        }
    }
}
