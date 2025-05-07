package com.example.HeartDisease.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {
    private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);

    @Autowired
    private JavaMailSender mailSender;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            logger.info("Testing mail connection...");
            ((JavaMailSenderImpl) mailSender).getJavaMailProperties().forEach((key, value) ->
                    logger.info("Mail Property - {}: {}", key, value));
        } catch (Exception e) {
            logger.error("Mail configuration test failed", e);
        }
    }
}