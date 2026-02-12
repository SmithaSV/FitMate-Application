package com.fitmate.service;

import com.fitmate.dto.UserRequestDto;
import com.fitmate.dto.UserResponseDto;
import com.fitmate.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<UserResponseDto> findByEmail(String email);
    UserResponseDto registerUser(UserRequestDto requestDto);
    Optional<User> findEntityByEmail(String email);
    UserResponseDto getCurrentUser(String email);
    void forgotPassword(String email, String newPassword);

}
