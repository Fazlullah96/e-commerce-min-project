package com.example.component;

import com.example.dtos.OrderItemRequest;
import com.example.dtos.OrderItemResponse;
import com.example.dtos.OrderRequest;
import com.example.dtos.OrderResponse;
import com.example.models.Order;
import com.example.models.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {
    public OrderItem toOrderItemModel(OrderItemRequest request){
        return OrderItem
                .builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem request){
        return OrderItemResponse
                .builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .build();
    }

    public Order toOrderModel(OrderRequest request){
        return Order
                .builder()
                .build();
    }

    public OrderResponse toOrderResponse(Order request){
        return OrderResponse
                .builder()
                .orderNumber(request.getOrderNumber())
                .userId(request.getUserId())
                .orderDate(request.getOrderData())
                .status(request.getStatus())
                .totalAmount(request.getTotalAmount())
                .orderItemResponses(request
                        .getOrderItems()
                        .stream()
                        .map(this::toOrderItemResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
