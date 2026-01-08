package com.codexconquer.timetracking.controller;

import com.codexconquer.timetracking.dto.RegisterRequest;
import com.codexconquer.timetracking.entity.User;
import com.codexconquer.timetracking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.codexconquer.timetracking.dto.LoginRequest;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }


    @PostMapping("/login")
    public User login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

}
