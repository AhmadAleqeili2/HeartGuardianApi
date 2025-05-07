package com.example.HeartDisease.service;

import com.example.HeartDisease.model.NotificationModel;
import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.errorandmessage.Message;
import com.example.HeartDisease.model.dto.user.Notification;
import com.example.HeartDisease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    UserRepository userRepository;
    public  ResponseEntity<?> addNotification(Notification notification, Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName());
        if (notification != null || notification.getMessage() != null ){
            NotificationModel addNotification = new NotificationModel();
            addNotification.setDate(LocalDateTime.now());
            addNotification.setMessage(notification.getMessage());
            addNotification.setUser(user);
            user.getNotification().add(addNotification);
            userRepository.save(user);
            Message message = new Message();
            message.setMessage("Notification added");
            message.setCode(HttpStatus.OK.value());
            return ResponseEntity.ok(message);
        }
        Message error = new Message();
        error.setMessage("Notification not added");
        error.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }

    public  ResponseEntity<?> getNotifications(Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName());

        if (user == null || user.getNotification().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Notification> notificationsDtos = user.getNotification().stream().map(NotificationModel -> {
            Notification dto = new Notification();
            dto.setId(NotificationModel.getId());
            dto.setMessage(NotificationModel.getMessage());
            dto.setDate(NotificationModel.getDate());
            return dto;
        }).toList();

        return ResponseEntity.ok(notificationsDtos);
    }
}
