package com.example.HeartDisease.model.dto.user;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Feedback {
    @NotBlank(message = "feedback is required")
    private String feedback;
    private LocalDateTime date;

}
