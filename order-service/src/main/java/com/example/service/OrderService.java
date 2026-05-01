package com.example.service;

import com.example.clients.ProductClient;
import com.example.clients.UserClient;
import com.example.component.Mapper;
import com.example.dtos.*;
import com.example.exceptions.OrderNotFoundException;
import com.example.exceptions.OutOfStockException;
import com.example.models.Order;
import com.example.models.OrderItem;
import com.example.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final Mapper mapper;

    @Caching(put = {
            @CachePut(value = "ORDER_ORDERNUMBER_CACHE", key = "#result.orderNumber"),
            @CachePut(value = "ORDER_USERID_CACHE", key = "#result.userId")
    }, evict = {
            @CacheEvict(value = "ORDER_CACHE_LIST", allEntries = true),
            @CacheEvict(value = "ORDER_USERID_CACHE", key = "#result.userId")
    })
    public OrderResponse addOrder(OrderRequest orderRequest){
        UserResponse user = userClient.getUserById(orderRequest.getUserId());
        Order order = Order
                .builder()
                .userId(orderRequest.getUserId())
                .orderNumber(generateRandomOrderNumber())
                .orderData(LocalDate.now())
                .status(Order.Status.PENDING)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;
        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItemsRequests()){
            ProductResponse product = productClient.getProductById(orderItemRequest.getProductId());
            if(product.getStockQuantity() < orderItemRequest.getQuantity()){
                throw new OutOfStockException("Out of Stock Product: " + product.getName());
            }
            OrderItem orderItem = OrderItem
                    .builder()
                    .productId(orderItemRequest.getProductId())
                    .quantity(orderItemRequest.getQuantity())
                    .unitPrice(product.getPrice())
                    .order(order)
                    .build();

            orderItems.add(orderItem);
            totalAmount += (product.getPrice()) * orderItemRequest.getQuantity();
            product.setStockQuantity(product.getStockQuantity() - orderItemRequest.getQuantity());
            ProductResponse productResponse = productClient.updateQuantity(product.getId(), product);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        return mapper.toOrderResponse(orderRepo.save(order));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "ORDER_ORDERNUMBER_CACHE", key = "#orderNumber")
    public OrderResponse getOrderByOrderNumber(int orderNumber){
        Order order = orderRepo.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for OrderNumber: " + orderNumber));
        return mapper.toOrderResponse(order);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "ORDER_CACHE_LIST", key = "'ALL'")
    public List<OrderResponse> getAllOrders(){
        List<Order> orders = orderRepo.findAll();
        return orders
                .stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "ORDER_USERID_CACHE", key = "#userId")
    public List<OrderResponse> getOrderByUserId(int userId){
        List<Order> orders = orderRepo.findAllByUserId(userId);
        return orders
                .stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "ORDER_ORDERNUMBER_CACHE", key = "#orderNumber"),
            @CacheEvict(value = "ORDER_USERID_CACHE", key = "#result.userId"),
            @CacheEvict(value = "ORDER_CACHE_LIST", allEntries = true)
    })
    public OrderResponse deleteOrderByOrderNumber(int orderNumber){
        Order order = orderRepo.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException("Order not found for OrderNumber: " + orderNumber));
        orderRepo.delete(order);
        return mapper.toOrderResponse(order);
    }

    public Integer generateRandomOrderNumber(){
        return ThreadLocalRandom.current().nextInt(100000, 999999);
    }
}
