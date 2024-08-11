package com.example.springbootwebtutorial.springbootwebtutorial.dto;

import com.example.springbootwebtutorial.springbootwebtutorial.Annotation.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "name cannot be null")
    @Size(min = 3,max = 10,message = "Number of char min = 3 and max = 10")
    private String name;
    @NotBlank(message = "email of employee cannot be blank")
    @Email(message = "Please enter a valid emain")
    private String email;

    @NotBlank(message = "Role of employee cannot be blank")
    //@Pattern(regexp = "^(ADMIN|USER)$",message = "Role of employee can be user of admin")
    @EmployeeRoleValidation
    private String role;

    @Max(value = 80,message = "Age cannot be greater than 80")
    @Min(value = 18,message = "Age cannot be lesser than 18")
    private Integer age;

    @PastOrPresent(message = "date of joining cannot be in future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee should active")
    @JsonProperty("isActive")
    private Boolean isActive;

    @Positive(message = "Salary can only be positive")
    @Digits(integer = 6,fraction = 2, message = "Salary can be in form of xxxxx.yy")
    private Double salary;
}
