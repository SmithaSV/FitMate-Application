package com.fitmate.controller;

import com.fitmate.dto.UserRequestDto;
import com.fitmate.dto.UserResponseDto;
import com.fitmate.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto requestDto){
        UserResponseDto response =userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(Authentication authentication){
        String email=authentication.getName();
        UserResponseDto response=userService.getCurrentUser(email);
        return ResponseEntity.ok(response);

    }
    }






