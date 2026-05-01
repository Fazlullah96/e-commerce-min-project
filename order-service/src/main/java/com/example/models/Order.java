package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderNumber;
    private Integer userId;
    private LocalDate orderData;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Double totalAmount;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<OrderItem> orderItems;


    public enum Status{
        PENDING,
        COMPLETED,
        CANCELED
    }
}
