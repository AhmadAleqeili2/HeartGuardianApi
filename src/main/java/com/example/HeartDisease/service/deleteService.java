package com.example.HeartDisease.service;

import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class deleteService {
    private static final Logger logger = LoggerFactory.getLogger(deleteService.class);

    private static UserRepository userRepository;

    @Autowired
    public deleteService(UserRepository userRepository) {
        deleteService.userRepository = userRepository;
    }

    public static ResponseEntity<Object> deleteacc(Authentication authentication) {
        if (authentication == null) {
            logger.error("Authentication object is null");
            return ResponseEntity.badRequest().body("Authentication required");
        }

        String email = authentication.getName();
        logger.info("Attempting to delete account for user: {}", email);

        try {
            Users user = userRepository.findByEmail(email);

            if (user == null) {
                logger.error("User not found for email: {}", email);
                return ResponseEntity.badRequest().body("User not found");
            }

            userRepository.save(user);
            userRepository.delete(user);
            userRepository.flush();
            logger.info("Successfully deleted user account: {}", email);
            return ResponseEntity.ok().body("Account successfully deleted");

        } catch (Exception e) {
            logger.error("Error deleting account for user: {}", email, e);
            return ResponseEntity.internalServerError().body("Failed to delete account: " + e.getMessage());
        }
    }
}

