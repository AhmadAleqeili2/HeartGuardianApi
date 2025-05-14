package com.example.HeartDisease.controller;
import com.example.HeartDisease.model.dto.errorandmessage.Message;
import com.example.HeartDisease.model.dto.user.History;
import com.example.HeartDisease.model.dto.user.Notification;
import com.example.HeartDisease.service.*;
import com.example.HeartDisease.model.dto.user.Feedback;
import com.example.HeartDisease.model.dto.user.UserInfo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserDataController {
    @Autowired
    private ErrorService errorService;
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private UserProfile userProfile;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private NotificationService NotificationService;

    @GetMapping("/user")
    public ResponseEntity<UserInfo> getMyInfo(Authentication authentication) {

        return userProfile.getInfo(authentication);
    }

    @PostMapping("/feedback")
    public ResponseEntity<?> sentFeedBack(Authentication authentication, @Valid @RequestBody Feedback feedback, BindingResult result) {
        ResponseEntity<Object> hasError = errorService.check(result);
        if (hasError != null) return hasError;
        return feedBackService.setfeedback(feedback, authentication);
    }

    @GetMapping("/feedback")
    public ResponseEntity<?> getFeedBack(Authentication authentication) {
        return feedBackService.getfeedback(authentication);
    }

    @PostMapping("/history")
    public ResponseEntity<?> addHistory(Authentication authentication, @Valid @RequestBody History history, BindingResult result) {
        ResponseEntity<Object> hasError = errorService.check(result);
        if (hasError != null) return hasError;
        return historyService.addHistory(history, authentication);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistorys(Authentication authentication) {
        return historyService.getHistorys(authentication);
    }

    @PostMapping("/notification")
    public ResponseEntity<?> addNotification(Authentication authentication, @Valid @RequestBody Notification notification, BindingResult result) {
        ResponseEntity<Object> hasError = errorService.check(result);
        if (hasError != null) return hasError;
        return NotificationService.addNotification(notification, authentication);
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getNotification(Authentication authentication) {
        return NotificationService.getNotifications(authentication);
    }

    @PostMapping("/uploadimage")
    public ResponseEntity<Message> uploadImage(Authentication authentication,
                                               @RequestParam("image") MultipartFile imageFile) {
        return userProfile.uploadImage(authentication, imageFile);
    }


    @GetMapping("uploadimage")
    public ResponseEntity<?> getUserImage(Authentication authentication) {
        return userProfile.getImage(authentication);
    }
}