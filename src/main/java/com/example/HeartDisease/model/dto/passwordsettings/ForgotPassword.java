package com.example.HeartDisease.model.dto.passwordsettings;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPassword {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email")
    private String Email;
}

