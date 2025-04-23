package com.example.HeartDisease.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "feedback")
public class FeedbackModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String feedback;
    private LocalDateTime date ;
    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    @ToString.Exclude
    private Users user;

}

