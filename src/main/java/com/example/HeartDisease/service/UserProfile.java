package com.example.HeartDisease.service;

import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.user.UserInfo;
import com.example.HeartDisease.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserProfile {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserInfo> getInfo(Authentication authentication) {


        Users user = userRepository.findByEmail(authentication.getName());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserInfo response = new UserInfo();
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        if (user.getNotification() != null && !user.getNotification().isEmpty()) {
            response.setLastLogin(user.getNotification().get(user.getNotification().size() - 1).getDate().toString());
        } else {
            response.setLastLogin("No login yet");
        }

        if (user.getHistory() != null && !user.getHistory().isEmpty()) {
            response.setLastDiagnosis(user.getHistory().get(user.getHistory().size() - 1).getDate().toString());
        } else {
            response.setLastDiagnosis("No diagnosis yet");
        }

        return ResponseEntity.ok(response);
    }
}
