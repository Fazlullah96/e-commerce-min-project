package com.example.controller;

import com.example.dtos.UserRequest;
import com.example.dtos.UserResponse;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest request){
        return new ResponseEntity<>(userService.addUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable int id){
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username){
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserResponse>> getActiveUsers(){
        return new ResponseEntity<>(userService.getActiveUsers(), HttpStatus.OK);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<UserResponse>> getInActiveUsers(){
        return new ResponseEntity<>(userService.getInActiveUsers(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
