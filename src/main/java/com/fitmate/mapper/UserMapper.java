package com.fitmate.mapper;

import com.fitmate.dto.UserRequestDto;
import com.fitmate.dto.UserResponseDto;
import com.fitmate.entity.User;

public class UserMapper {
    public static User toEntity(UserRequestDto dto){
        if(dto==null) return null;
        User user=new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole("USER");
        user.setEnabled(true);
        return user;
    }
    public static UserResponseDto toResponseDto(User user){
        if(user==null) return null;
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                 .email(user.getEmail())
                .build();
}
}