package com.example.component;

import com.example.dtos.ProductRequest;
import com.example.dtos.ProductResponse;
import com.example.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public Product toProductModel(ProductRequest request){
        return Product
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .isActive(true)
                .build();
    }

    public ProductResponse toProductResponse(Product request){
        return ProductResponse
                .builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .isActive(request.getIsActive())
                .build();
    }
}
