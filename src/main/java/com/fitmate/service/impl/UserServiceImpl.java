package com.fitmate.service.impl;

import com.fitmate.Repository.UserRepository;
import com.fitmate.dto.UserRequestDto;
import com.fitmate.dto.UserResponseDto;
import com.fitmate.entity.User;
import com.fitmate.mapper.UserMapper;
import com.fitmate.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncder, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User savedUser=userRepository.save(user);
        return UserMapper.toResponseDto(savedUser);




    }

}
