package com.example.HeartDisease.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 2000; // 2 seconds

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendResetCodeEmail(String to, String code) {
        int attempts = 0;
        Exception lastException = null;

        while (attempts < MAX_RETRIES) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(fromEmail);
                message.setTo(to);
                message.setSubject("Password Reset Code");
                message.setText("Your verification code is: " + code + "\nThis code will expire in 10 minutes.");

                logger.info("Attempt {} - Sending email to: {} with code: {}", attempts + 1, to, code);
                mailSender.send(message);
                logger.info("Reset code email sent successfully to: {}", to);
                return; // Success, exit method

            } catch (Exception e) {
                lastException = e;
                attempts++;
                logger.warn("Attempt {} failed to send email to: {}. Error: {}", attempts, to, e.getMessage());

                if (attempts < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        logger.error("Failed to send reset code email to: {} after {} attempts", to, MAX_RETRIES);
        throw new RuntimeException("Failed to send email after " + MAX_RETRIES + " attempts", lastException);
    }
}