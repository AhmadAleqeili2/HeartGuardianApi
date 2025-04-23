package com.example.HeartDisease.controller;
import com.example.HeartDisease.model.dto.History;
import com.example.HeartDisease.model.dto.Notification;
import com.example.HeartDisease.service.NotificationService;
import com.example.HeartDisease.model.dto.Feedback;
import com.example.HeartDisease.service.HistoryService;
import com.example.HeartDisease.model.dto.UserInfo;
import com.example.HeartDisease.service.FeedBackService;
import com.example.HeartDisease.service.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDataController {
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
    public ResponseEntity<?> sentFeedBack(Authentication authentication , @RequestBody Feedback feedback) {
        return feedBackService.setfeedback(feedback,authentication);
    }
    @GetMapping("/feedback")
    public ResponseEntity<?> getFeedBack(Authentication authentication){
        return feedBackService.getfeedback(authentication);
    }
    @PostMapping("/history")
    public ResponseEntity<?> addHistory(Authentication authentication , @RequestBody History history) {
        return historyService.addHistory(history,authentication);
    }
    @GetMapping("/history")
    public ResponseEntity<?> getHistorys(Authentication authentication){
        return historyService.getHistorys(authentication);
    }
    @PostMapping("/notification")
    public ResponseEntity<?> addNotification(Authentication authentication , @RequestBody Notification notification) {
        return NotificationService.addNotification(notification,authentication);
    }
    @GetMapping("/notification")
    public ResponseEntity<?> getNotification(Authentication authentication){
        return NotificationService.getNotifications(authentication);
    }
}
