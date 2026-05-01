package com.example.component;
import com.example.dtos.UserRequest;
import com.example.dtos.UserResponse;
import com.example.models.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public User toUserModel(UserRequest request){
        return User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(User.Role.CUSTOMER)
                .build();
    }

    public UserResponse toUserResponse(User request){
        return UserResponse
                .builder()
                .id(request.getId())
                .username(request.getUsername())
                .email(request.getEmail())
                .role(request.getRole())
                .isActive(request.getIsActive())
                .build();
    }
}
