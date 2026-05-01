package com.example.service;

import com.example.component.Mapper;
import com.example.dtos.UserRequest;
import com.example.dtos.UserResponse;
import com.example.exception.UserAlreadyExistsException;
import com.example.models.User;
import com.example.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    private final Mapper mapper;

    public UserResponse addUser(UserRequest request){
        boolean isUserExistsByEmail = userRepo.existsByEmail(request.getEmail());
        if(isUserExistsByEmail){
            throw new UserAlreadyExistsException("User Already exists with Email : " + request.getEmail());
        }
        User savedUser = userRepo.save(mapper.toUserModel(request));
        return mapper.toUserResponse(savedUser);
    }
}
