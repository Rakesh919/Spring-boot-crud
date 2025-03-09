package com.example.project.annotations;

import jakarta.validation.Payload;

public @interface UniqueEmail {

    String message() default "Email already Exists, Please use a different Email Id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};
}
