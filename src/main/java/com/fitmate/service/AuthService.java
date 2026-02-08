package com.fitmate.service;

import com.fitmate.dto.LoginRequestDto;

public interface AuthService {
    String login(LoginRequestDto requestDto);
}
