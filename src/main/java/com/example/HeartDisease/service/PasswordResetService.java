package com.example.HeartDisease.service;

import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.errorandmessage.ErrorMessage;
import com.example.HeartDisease.model.dto.errorandmessage.Message;
import com.example.HeartDisease.model.dto.passwordsettings.ResetPasswordRequest;
import com.example.HeartDisease.repository.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    public void initiatePasswordReset(String email) {
        logger.info("Initiating password reset for email: {}", email);
        Users user = userRepository.findByEmail(email);
        if (user != null) {
            try {
                String code = generate6DigitCode();
                logger.debug("Generated reset code: {} for email: {}", code, email);
                user.setResetCode(code);
                user.setResetCodeExpiration(LocalDateTime.now().plusMinutes(10));
                userRepository.save(user);
                logger.debug("User updated with reset code in database");
                emailService.sendResetCodeEmail(user.getEmail(), code);
                logger.info("Password reset initiated successfully for email: {}", email);
            } catch (Exception e) {
                logger.error("Error during password reset process for email: " + email, e);
                throw e;
            }
        } else {
            logger.info("No user found with email: {}", email);
            throw new IllegalArgumentException("No user found with email: " + email);
        }
    }

    private String generate6DigitCode() {
        int code = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(code);
    }

    public ResponseEntity<Object> resetPasswordRequest(@Valid ResetPasswordRequest request) {
    Users user = userRepository.findByEmail(request.getEmail());
    if (request.getCode().equals(user.getResetCode()) && LocalDateTime.now().isBefore(user.getResetCodeExpiration())) {
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetCode(null);
        user.setResetCodeExpiration(null);
        userRepository.save(user);
        Message message = new Message();
        message.setMessage("Password reset successful");
        message.setCode(200);
        return ResponseEntity.ok().body(message);
    }
    else {
        ErrorMessage error = new ErrorMessage();
        error.setMessage("Invalid reset code");
        error.setCode(400);
        return ResponseEntity.badRequest().body(error);
    }
    }
}