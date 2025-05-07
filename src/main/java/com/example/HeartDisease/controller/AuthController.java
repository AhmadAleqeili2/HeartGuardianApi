package com.example.HeartDisease.controller;

// AuthController.java (Controller)
import com.example.HeartDisease.model.dto.passwordsettings.UpdatePasswd;
import com.example.HeartDisease.model.dto.user.UserLoginRequest;
import com.example.HeartDisease.model.dto.user.UserRegistrationRequest;
import com.example.HeartDisease.service.AuthService;
import com.example.HeartDisease.service.ErrorService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private ErrorService errorService;

    @GetMapping("/")
    public String Hello(){
        return "Welcom to Heart Api";
    }
    // Existing login endpoint
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLoginRequest loginRequest, BindingResult result) {
        ResponseEntity<Object>  hasError = errorService.check(result);
        if(hasError != null) return hasError;
        return authService.authenticate(loginRequest);
    }
    @PutMapping("/updatepasswd")
    public ResponseEntity<Object> updatePassword( Authentication authentication ,@Valid @RequestBody UpdatePasswd newPassword, BindingResult result){
        ResponseEntity<Object>  hasError = errorService.check(result);
        if(hasError != null) return hasError;
        return authService.updatePasswd(authentication , newPassword);
    }
    // New registration endpoint
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegistrationRequest registrationRequest, BindingResult result) {
        ResponseEntity<Object>  hasError = errorService.check(result);
        if(hasError != null) return hasError;
        return authService.registerUser(registrationRequest);

    }


}
