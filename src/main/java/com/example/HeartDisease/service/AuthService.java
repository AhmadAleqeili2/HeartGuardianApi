package com.example.HeartDisease.service;
// AuthService.java (Service)
import com.example.HeartDisease.config.JwtTokenProvider;
import com.example.HeartDisease.model.Users;
import com.example.HeartDisease.model.dto.UserLoginRequest;
import com.example.HeartDisease.model.dto.UserRegistrationRequest;
import com.example.HeartDisease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<String> authenticate(UserLoginRequest loginRequest) {
        Users user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(jwtTokenProvider.generateToken(loginRequest.getEmail()));
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    public ResponseEntity<String> registerUser(UserRegistrationRequest registrationRequest) {
        // Check if the username already exists
        if (userRepository.findByEmail(registrationRequest.getEmail()) != null) {
            return ResponseEntity.badRequest().body("User with this username already exists");
        }


        // Create a new user and hash the password
        Users user = new Users();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setFirstname(registrationRequest.getFirstname());
        user.setLastname(registrationRequest.getLastname());


        // Save the user in the database
        userRepository.save(user);
       return ResponseEntity.ok("User registered successfully!");
    }


}
