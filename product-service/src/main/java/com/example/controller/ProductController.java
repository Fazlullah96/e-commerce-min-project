package com.example.controller;

import com.example.dtos.ProductRequest;
import com.example.dtos.ProductResponse;
import com.example.entity.Product;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request){
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductResponse> getProductByName(@PathVariable String name){
        return new ResponseEntity<>(productService.getProductByName(name), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<ProductResponse> deleteProductById(@PathVariable int id){
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }

    @PutMapping("/quantity/update/{id}")
    public ResponseEntity<ProductResponse> updateQuantity(@PathVariable int id, @RequestBody ProductResponse request){
        return new ResponseEntity<>(productService.updateQuantity(id, request), HttpStatus.OK);
    }
}
