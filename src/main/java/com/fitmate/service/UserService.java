package com.fitmate.service;

import com.fitmate.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User save(User user);
}
