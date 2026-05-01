package com.example.service;

import com.example.component.Mapper;
import com.example.dtos.UserRequest;
import com.example.dtos.UserResponse;
import com.example.exception.UserAlreadyExistsException;
import com.example.exception.UserNotFoundException;
import com.example.models.User;
import com.example.repo.UserRepo;
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
public class UserService {
    private final UserRepo userRepo;
    private final Mapper mapper;

    @Transactional
    @Caching(put = {
            @CachePut(value = "USER_CACHE", key = "#result.id")
    }, evict = {
            @CacheEvict(value = "ACTIVE_USER_LIST_CACHE", allEntries = true),
            @CacheEvict(value = "ALL_USER_CACHE_LIST", allEntries = true)
    })
    public UserResponse addUser(UserRequest request){
        boolean isUserExistsByEmail = userRepo.existsByEmail(request.getEmail());
        if(isUserExistsByEmail){
            throw new UserAlreadyExistsException("User Already exists with Email : " + request.getEmail());
        }
        User savedUser = userRepo.save(mapper.toUserModel(request));
        savedUser.setIsActive(true);
        return mapper.toUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "USER_CACHE", key = "#id")
    public UserResponse getUserById(int id){
        User user = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User not found for Id: " + id));
        return mapper.toUserResponse(user);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "USER_CACHE", key = "#id"),
            @CacheEvict(value = "ACTIVE_USER_CACHE_LIST", allEntries = true),
            @CacheEvict(value = "INACTIVE_USER_CACHE_LIST", allEntries = true),
            @CacheEvict(value = "USER_USERNAME_CACHE", key = "#result.username"),
            @CacheEvict(value = "USER_EMAIL_CACHE", key = "#result.email")
    })
    public UserResponse deleteUserById(int id){
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for Id: " + id));
        user.setIsActive(false);
        return mapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "USER_USERNAME_CACHE", key = "#username")
    public UserResponse getUserByUsername(String username){
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found for Username: " + username));
        return mapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "USER_EMAIL_CACHE", key = "#email")
    public UserResponse getUserByEmail(String email){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found for Email: " + email));
        return mapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "ACTIVE_USER_CACHE_LIST", key = "'ACTIVE'")
    public List<UserResponse> getActiveUsers(){
        List<User> users = userRepo.findAllByIsActiveTrue();
        return users
                .stream()
                .map(mapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "INACTIVE_USER_CACHE_LIST", key = "'INACTIVE'")
    public List<UserResponse> getInActiveUsers(){
        List<User> users = userRepo.findAllByIsActiveFalse();
        return users
                .stream()
                .map(mapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "ALL_USER_CACHE_LIST", key = "'ALL'")
    public List<UserResponse> getAllUsers(){
        List<User> users = userRepo.findAll();
        return users
                .stream()
                .map(mapper::toUserResponse)
                .collect(Collectors.toList());
    }
}
