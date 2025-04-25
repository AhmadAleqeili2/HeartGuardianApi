package com.example.HeartDisease.service;
// AuthService.java (Service)
import com.example.HeartDisease.config.JwtTokenProvider;
import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.Message;
import com.example.HeartDisease.model.dto.UserLoginRequest;
import com.example.HeartDisease.model.dto.UserRegistrationRequest;
import com.example.HeartDisease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<Object> authenticate(UserLoginRequest loginRequest) {
        Users user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Message message = new Message();
            message.setMessage(jwtTokenProvider.generateToken(loginRequest.getEmail()));
            message.setCode(HttpStatus.OK.value());
            return ResponseEntity.ok(message);
        } else {
            Message error = new Message();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("Invalid credentials");
            return ResponseEntity.badRequest().body(error);
        }
    }

    public ResponseEntity<Object> registerUser(UserRegistrationRequest registrationRequest) {
        // Check if the username already exists
        if (userRepository.findByEmail(registrationRequest.getEmail()) != null) {
            Message error = new Message();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage("User with this email already exists");
            return ResponseEntity.badRequest().body(error);
        }


        // Create a new user and hash the password
        Users user = new Users();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setFirstname(registrationRequest.getFirstname());
        user.setLastname(registrationRequest.getLastname());


        // Save the user in the database
        userRepository.save(user);
        Message message = new Message();
        message.setMessage("User registered successfully!");
        message.setCode(HttpStatus.OK.value());
       return ResponseEntity.ok(message);
    }


}
