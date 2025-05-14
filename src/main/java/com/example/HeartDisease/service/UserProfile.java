package com.example.HeartDisease.service;

import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.errorandmessage.Message;
import com.example.HeartDisease.model.dto.user.UserInfo;
import com.example.HeartDisease.repository.UserRepository;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;

@Service
public class UserProfile {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Message> uploadImage(Authentication authentication, MultipartFile imageFile) {
        try {
            Users user = userRepository.findByEmail(authentication.getName());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            user.setProfileImage(imageFile.getBytes());
            userRepository.save(user);
            Message message = new Message();
            message.setMessage("Image uploaded successfully");
            message.setCode(HttpStatus.OK.value());
            return ResponseEntity.ok(message);
        } catch (IOException e) {
            Message message = new Message();
            message.setMessage("Error uploading image: " + e.getMessage());
            message.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(message); }

    }

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

    public ResponseEntity<?> getImage(Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] image = user.getProfileImage();
        HttpHeaders headers = new HttpHeaders();

        if (image == null) {
            Message message = new Message();
            message.setMessage("There is no image for this user");
            message.setCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

        } else {
            // محاولة اكتشاف نوع الصورة
            try {
                String imageType = Files.probeContentType(
                        Files.createTempFile("temp", null).toAbsolutePath());

                if (imageType != null && imageType.equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                }
            } catch (IOException e) {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
        }

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}
