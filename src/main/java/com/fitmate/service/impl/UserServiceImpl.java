package com.fitmate.service.impl;

import com.fitmate.Repository.UserRepository;
import com.fitmate.dto.UserRequestDto;
import com.fitmate.dto.UserResponseDto;
import com.fitmate.mapper.UserMapper;
import com.fitmate.entity.User;   // ✔ KEEP — used for registerUser()
import com.fitmate.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserResponseDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toResponseDto);
    }

    @Override
    public UserResponseDto registerUser(UserRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        User user = UserMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.toResponseDto(savedUser);
    }

    @Override
    public Optional<User> findEntityByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDto getCurrentUser(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        return UserMapper.toResponseDto(user);
    }

    @Override
    public void forgotPassword(String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Email not registered"));
     String token=java.util.UUID.randomUUID().toString();
     user.setResetToken(token);
     user.setTokenExpiry(java.time.LocalDateTime.now().plusMinutes(15));
     userRepository.save(user);
     System.out.println("RESET TOKEN(DEV MODE): "+token);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user=userRepository.findByResetToken(token).orElseThrow(()->new RuntimeException("Invalid reset token"));
        if(user.getTokenExpiry()==null ||
        user.getTokenExpiry().isBefore(java.time.LocalDateTime.now())){
            throw new RuntimeException("Token expired");

        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);


    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.fitmate.entity.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_"+user.getRole())

        );
        //System.out.println("DB ROLE = " + user.getRole());
       // System.out.println("SPRING AUTHORITY = ROLE_" + user.getRole());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
                //java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
