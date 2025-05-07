package com.example.HeartDisease.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.HeartDisease.service.deleteService;
@RestController
public class DeleteAccController {


    @DeleteMapping("/deleteacc")
    public ResponseEntity<Object> deleteAcc(Authentication authentication){
        return deleteService.deleteacc(authentication);
    }
}
