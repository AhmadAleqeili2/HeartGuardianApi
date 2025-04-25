package com.example.HeartDisease.service;

import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.UserInfo;
import com.example.HeartDisease.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserProfile {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserInfo> getInfo(Authentication authentication) {
        String email = authentication.getName();

        Users user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserInfo response = new UserInfo();
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setEmail(user.getEmail());
        response.setLastDiagnosis(user.getHistory().get(user.getHistory().size()-1).getDate());
        response.setLastLogin(user.getNotification().get(user.getNotification().size()-1).getDate());
        return ResponseEntity.ok(response);
    }
}
