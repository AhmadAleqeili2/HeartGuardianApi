package com.example.HeartDisease.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// UserLoginRequest.java
@Data
public class UserLoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;

}

