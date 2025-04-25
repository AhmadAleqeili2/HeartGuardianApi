package com.example.HeartDisease.service;
import com.example.HeartDisease.model.FeedbackModel;
import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.Feedback;
import com.example.HeartDisease.model.dto.Message;
import com.example.HeartDisease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeedBackService {
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<Object> setfeedback(Feedback feedback , Authentication authentication) {
        if(userRepository.findByEmail(authentication.getName()).getFeedback() == null) {
            FeedbackModel addfeedback = new FeedbackModel();
            Users user = userRepository.findByEmail(authentication.getName());
            addfeedback.setFeedback(feedback.getFeedback());
            addfeedback.setDate(LocalDateTime.now());
            addfeedback.setUser(user);
            user.setFeedback(addfeedback);
            userRepository.save(user);
            Message message = new Message();
            message.setMessage("Thank you for your feedback");
            message.setCode(HttpStatus.OK.value());
            return ResponseEntity.ok(message);
        }
        Message error = new Message();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("You already give feedback");
        return ResponseEntity.badRequest().body(error);
    }
   public ResponseEntity<?> getfeedback(Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName());
        if (user.getFeedback() ==null){
            return ResponseEntity.notFound().build();
        }
        Feedback feedback = new Feedback();
        feedback.setFeedback(user.getFeedback().getFeedback());
        feedback.setDate(user.getFeedback().getDate());
        return ResponseEntity.ok().body(feedback);

    }



}
