package com.example.HeartDisease.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class HistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String history;
    private String message;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Users user;
}
