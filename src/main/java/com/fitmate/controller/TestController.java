package com.fitmate.controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class TestController {

    @GetMapping("/profile")
    public String userProfile(Authentication authentication) {
        return "Hello " + authentication.getName() + " — User profile accessed";
    }
}

