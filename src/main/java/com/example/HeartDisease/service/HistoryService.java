package com.example.HeartDisease.service;
import com.example.HeartDisease.model.HistoryModel;
import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.History;
import com.example.HeartDisease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private UserRepository userRepository;
    public  ResponseEntity<?> addHistory(History history, Authentication authentication) {
        Users user =userRepository.findByEmail(authentication.getName());
        HistoryModel addHistory = new HistoryModel();
        if(history != null) {
            addHistory.setMessage(history.getMessage());
            addHistory.setHistory(history.getHistory());
            addHistory.setDate(LocalDateTime.now());
            addHistory.setUser(user);
            user.getHistory().add(addHistory);
            userRepository.save(user);

            return ResponseEntity.ok("History added");
        }
        return ResponseEntity.badRequest().body("History not added");
    }

    public ResponseEntity<?> getHistorys(Authentication authentication) {
        Users user = userRepository.findByEmail(authentication.getName());

        if (user == null || user.getHistory().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<History> historyDtos = user.getHistory().stream().map(historyModel -> {
            History dto = new History();
            dto.setId(historyModel.getId());
            dto.setHistory(historyModel.getHistory());
            dto.setMessage(historyModel.getMessage());
            dto.setDate(historyModel.getDate());
            return dto;
        }).toList();

        return ResponseEntity.ok(historyDtos);
    }

}
