package com.example.HeartDisease.service;

import com.example.HeartDisease.model.dto.errorandmessage.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ErrorService {
    public ResponseEntity<Object> check(BindingResult result) {
        if (result.hasErrors()) {
            // إذا كانت البيانات موجودة ولكن هناك أخطاء في التحقق من الصحة
            String errorMessage = result.getFieldError().getDefaultMessage();

            ErrorMessage error = new ErrorMessage();
            error.setCode(HttpStatus.BAD_REQUEST.value()); // كود الخطأ 400
            error.setMessage(errorMessage); // وضع رسالة الخطأ

            return ResponseEntity.badRequest().body(error); // إعادة الخطأ كردّ JSON
        }
        else return null;
    }
}
