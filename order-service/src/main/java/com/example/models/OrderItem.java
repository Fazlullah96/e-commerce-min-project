package com.example.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private Double unitPrice;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;
}
