package com.example.HeartDisease.model;// User.java (Model)
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Users {

    private String password;
    private String firstname;
    private String lastname;
    @Id
    private String email;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private FeedbackModel feedback;
    private String resetCode;
    private LocalDateTime resetCodeExpiration;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<HistoryModel> history;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<NotificationModel> notification;

// getters and setters
}
