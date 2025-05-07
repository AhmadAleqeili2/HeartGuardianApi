package com.example.HeartDisease.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {
    private Long id;
    @NotBlank(message = "message is required")
    private String message;
    private LocalDateTime date;

}