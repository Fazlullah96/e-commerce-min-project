package com.example.repo;

import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByIdAndEmail(int id, String email);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllByIsActiveTrue();
    List<User> findAllByIsActiveFalse();
}
