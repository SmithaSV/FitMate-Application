package com.fitmate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class LoginResponseDto {
    @Getter
    private String token;
    public LoginResponseDto(String token){
        this.token=token;
    }
    public String getToken(){
        return token;
    }
}
