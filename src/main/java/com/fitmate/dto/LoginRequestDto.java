package com.fitmate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
    @NotBlank(message="Email is required")
    @Email(message="Invalid email")
    private String email;
    @NotBlank(message="password is required")
    private String password;

}
