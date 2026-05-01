package com.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @Email
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private Boolean isActive;


    public enum Role{
        CUSTOMER,
        ADMIN
    }
}
