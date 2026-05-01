package com.example.clients;

import com.example.component.UserClientFallbackFactory;
import com.example.dtos.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {
    @GetMapping("/api/user/{id}")
    UserResponse getUserById(@PathVariable("id") int id);
}
