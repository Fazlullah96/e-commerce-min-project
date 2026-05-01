package com.example.clients;

import com.example.component.ProductClientFallbackFactory;
import com.example.dtos.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", fallbackFactory = ProductClientFallbackFactory.class)
public interface ProductClient {
    @GetMapping("/api/product/{id}")
    ProductResponse getProductById(@PathVariable("id") int id);
    @PutMapping("/api/product/quantity/update/{id}")
    ProductResponse updateQuantity(@PathVariable("id") int id ,@RequestBody ProductResponse response);
}

