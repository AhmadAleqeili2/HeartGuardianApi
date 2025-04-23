package com.example.HeartDisease.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Users user;
}
