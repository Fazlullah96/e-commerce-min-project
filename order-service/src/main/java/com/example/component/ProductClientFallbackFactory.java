package com.example.component;

import com.example.clients.ProductClient;
import com.example.dtos.ProductResponse;
import com.example.exceptions.ProductNotFoundException;
import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {
    @Override
    public ProductClient create(Throwable cause) {
        return new ProductClient() {
            @Override
            public ProductResponse getProductById(int id) {
                if(cause instanceof FeignException.NotFound){
                    throw new ProductNotFoundException("Product not found!");
                }
                throw new RuntimeException("Service not available");
            }

            @Override
            public ProductResponse updateQuantity(int id, ProductResponse response) {
                throw new RuntimeException("Service not available");
            }
        };
    }
}
