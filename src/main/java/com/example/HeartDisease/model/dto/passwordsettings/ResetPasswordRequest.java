package com.example.HeartDisease.model.dto.passwordsettings;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email")
    private String Email;
    @NotBlank(message = "code is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Code must be exactly 6 digits")
    private String code;
    @NotBlank(message = "password is required")
    @Size(min = 8, message = "Password should be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String newPassword;
}
