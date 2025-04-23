package com.example.HeartDisease.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {
    private Long id;
    private String message;
    private LocalDateTime date;

}