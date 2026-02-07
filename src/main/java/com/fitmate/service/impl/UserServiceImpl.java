package com.fitmate.service.impl;

import com.fitmate.Repository.UserRepository;
import com.fitmate.dto.UserRequestDto;
import com.fitmate.dto.UserResponseDto;
import com.fitmate.entity.User;
import com.fitmate.mapper.UserMapper;
import com.fitmate.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserResponseDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toResponseDto);
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto requestDto){
        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }
        User user= UserMapper.toEntity(requestDto);
        User savedUser=userRepository.save(user);
        return UserMapper.toResponseDto(savedUser);


    }

}
