package com.fitmate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
@NotBlank(message="Name is required")
    private String name;
@NotBlank(message="email is required")
@Email(message="Invalid email")
    private String email;
@NotBlank(message="password is required")
    private String password;
}
