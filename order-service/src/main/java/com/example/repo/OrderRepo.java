package com.example.repo;

import com.example.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderNumber(int orderNumber);
    List<Order> findAllByUserId(int userId);
}
