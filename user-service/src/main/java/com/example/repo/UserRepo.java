package com.example.repo;

import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByIdAndEmail(int id, String email);
    boolean existsByEmail(String email);
}
