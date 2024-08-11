package com.example.springbootwebtutorial.springbootwebtutorial.Annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Min;

import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation,String> {

    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext constraintValidatorContext) {
        List<String> roles = List.of("USER","ADMIN");
        return roles.contains(inputRole);
    }
}
