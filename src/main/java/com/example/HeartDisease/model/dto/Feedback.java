package com.example.HeartDisease.model.dto;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Feedback {
    private String feedback;
    private LocalDateTime date;

}
