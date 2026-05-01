package com.example.dtos;

import com.example.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Integer orderNumber;
    private Integer userId;
    private LocalDate orderDate;
    private Order.Status status;
    private Double totalAmount;
    private List<OrderItemResponse> orderItemResponses;
}
