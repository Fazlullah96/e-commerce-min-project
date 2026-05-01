package com.example.service;

import com.example.component.Mapper;
import com.example.dtos.ProductRequest;
import com.example.dtos.ProductResponse;
import com.example.entity.Product;
import com.example.exceptions.ProductAlreadyExistsException;
import com.example.exceptions.ProductNotFoundException;
import com.example.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepo productRepo;
    private final Mapper mapper;

    @Transactional
    @Caching(put = {
            @CachePut(value = "PRODUCT_CACHE", key = "#result.id"),
            @CachePut(value = "PRODUCT_NAME_CACHE", key = "#result.name")
    }, evict = {
            @CacheEvict(value = "PRODUCT_CACHE_LIST", allEntries = true)
    })
    public ProductResponse addProduct(ProductRequest request){
        boolean exists = productRepo.existsByName(request.getName());
        if(exists){
            throw new ProductAlreadyExistsException("Product Already exists with Name: " + request.getName());
        }
        Product product = productRepo.save(mapper.toProductModel(request));
        return mapper.toProductResponse(product);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "PRODUCT_CACHE", key = "#id")
    public ProductResponse getProductById(int id){
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for Id: " + id));
        return mapper.toProductResponse(product);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "PRODUCT_NAME_CACHE", key = "#name")
    public ProductResponse getProductByName(String name){
        Product product = productRepo.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for Name: " + name));
        return mapper.toProductResponse(product);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "PRODUCT_CACHE_LIST", key = "'ALL'")
    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepo.findAll();
        return products
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "PRODUCT_CACHE", key = "#id")
    public ProductResponse deleteProduct(int id){
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for Id: " + id));
        product.setIsActive(false);
        return mapper.toProductResponse(productRepo.save(product));
    }

    public ProductResponse updateQuantity(int id, ProductResponse request){
        Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not found for Id: " + id));
        Product updatedProduct = Product
                .builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .isActive(request.getIsActive())
                .build();

        return mapper.toProductResponse(productRepo.save(updatedProduct));
    }
}
