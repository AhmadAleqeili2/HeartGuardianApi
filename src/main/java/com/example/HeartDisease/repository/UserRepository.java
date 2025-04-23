package com.example.HeartDisease.repository;// UserRepository.java (Repository)
// UserRepository.java (Repository)
import com.example.HeartDisease.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users findByEmail(String email);
}
