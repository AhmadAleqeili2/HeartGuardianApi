package com.example.HeartDisease.model.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfo {
    private String email;
    private String firstname;
    private String lastname;
    private String lastDiagnosis;
    private String lastLogin;

}
