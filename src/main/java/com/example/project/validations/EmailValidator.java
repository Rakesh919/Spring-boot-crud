package com.example.project.validations;

import com.example.project.Repository.User.UserReposotory;
import com.example.project.annotations.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailValidator implements ConstraintValidator<UniqueEmail,String> {

    @Autowired
    private UserReposotory repository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
         if(repository.findByEmail(email)==null)
         {
             System.out.println(repository.findByEmail(email));
             return false;
         }

         return true;
    }
}
