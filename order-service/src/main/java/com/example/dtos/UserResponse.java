package com.example.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private Role role;
    private Boolean isActive;

    public enum Role{
        CUSTOMER,
        ADMIN
    }
}
