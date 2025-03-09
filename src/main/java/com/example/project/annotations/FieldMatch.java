package com.example.project.annotations;

import com.example.project.validations.FieldMatcherValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldMatcherValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {

    String message() default "Fields do Not Match";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};

    String first();
    String second();
}
