package com.fitmate.controller;

import com.fitmate.dto.ForgotPasswordRequestDto;
import com.fitmate.dto.LoginRequestDto;
import com.fitmate.dto.LoginResponseDto;
import com.fitmate.entity.User;
import com.fitmate.security.JwtUtil;
import com.fitmate.service.UserService;
import com.fitmate.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request){
        User user=userService.findEntityByEmail(request.getEmail())
                .orElseThrow(()->new BadCredentialsException("Invalid email"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        String token=jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponseDto(token));

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody @Valid ForgotPasswordRequestDto request) {

        userService.forgotPassword(
                request.getEmail(),
                request.getNewPassword()
        );

        return ResponseEntity.ok("Password reset successful");
    }
        }


