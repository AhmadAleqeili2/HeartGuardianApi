package com.example.HeartDisease.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class History {
    private Long id;
    private String history;
    private String message;
    private LocalDateTime date;
}

