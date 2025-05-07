package com.example.HeartDisease.controller;

import com.example.HeartDisease.model.dto.passwordsettings.ForgotPassword;
import com.example.HeartDisease.model.dto.errorandmessage.ErrorMessage;
import com.example.HeartDisease.model.dto.errorandmessage.Message;
import com.example.HeartDisease.model.dto.passwordsettings.ResetPasswordRequest;
import com.example.HeartDisease.service.ErrorService;
import com.example.HeartDisease.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.HeartDisease.config.JwtAuthenticationFilter.logger;

@RestController
public class ResetPasswordController {

    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private ErrorService errorService;

    @PostMapping("/forgotpassword")
    public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPassword request, BindingResult result) {
        ResponseEntity<Object>  hasError = errorService.check(result);
        if(hasError != null) return hasError;
        try {
            passwordResetService.initiatePasswordReset(request.getEmail());
            Message message = new Message();
            message.setMessage("If your email exists in our system, a reset code has been sent.");
            message.setCode(200);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            logger.error("Error processing password reset request :", e);
            ErrorMessage error = new ErrorMessage();
            error.setCode(500);
            return ResponseEntity.internalServerError().body(error);
        }
    }
    @PutMapping("/resetPasswordRequest")
    public ResponseEntity<Object> resetPasswordRequest(@Valid @RequestBody ResetPasswordRequest request , BindingResult result){
        ResponseEntity<Object>  hasError = errorService.check(result);
        if(hasError != null) return hasError;

        return passwordResetService.resetPasswordRequest(request);
    }
}