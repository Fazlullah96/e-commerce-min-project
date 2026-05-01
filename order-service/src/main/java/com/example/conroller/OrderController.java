package com.example.conroller;

import com.example.dtos.OrderRequest;
import com.example.dtos.OrderResponse;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest request){
        return new ResponseEntity<>(orderService.addOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/orderNumber/{orderNumber}")
    public ResponseEntity<OrderResponse> getOrderByOrderNumber(@PathVariable("orderNumber") int orderNumber){
        return new ResponseEntity<>(orderService.getOrderByOrderNumber(orderNumber), HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrderByUserId(@PathVariable("userId") int userId){
        return new ResponseEntity<>(orderService.getOrderByUserId(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllUsers(){
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }
}
