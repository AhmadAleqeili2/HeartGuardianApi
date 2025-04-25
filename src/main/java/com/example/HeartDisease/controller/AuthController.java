package com.example.HeartDisease.controller;

// AuthController.java (Controller)
import com.example.HeartDisease.model.dto.UserInfo;
import com.example.HeartDisease.model.dto.UserLoginRequest;
import com.example.HeartDisease.model.dto.UserRegistrationRequest;
import com.example.HeartDisease.service.AuthService;
import com.example.HeartDisease.service.UserProfile;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/")
    public String Hello(){
        return "Hello to Heart Api";
    }
    // Existing login endpoint
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }

    // New registration endpoint
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegistrationRequest registrationRequest) {
        return authService.registerUser(registrationRequest);

    }


}
