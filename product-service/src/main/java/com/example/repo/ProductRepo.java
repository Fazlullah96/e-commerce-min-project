package com.example.repo;

import com.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    boolean existsByName(String name);
}
