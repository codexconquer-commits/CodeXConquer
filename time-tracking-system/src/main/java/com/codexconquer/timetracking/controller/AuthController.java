package com.codexconquer.timetracking.controller;

import com.codexconquer.timetracking.dto.LoginRequest;
import com.codexconquer.timetracking.entity.User;
import com.codexconquer.timetracking.security.JwtUtil;
import com.codexconquer.timetracking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody @Valid LoginRequest request) {

        // Authenticate user
        User user = userService.login(request);

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        // Return token + basic user info
        return Map.of(
                "token", token,
                "id", user.getId(),
                "fullName", user.getFullName(),
                "email", user.getEmail(),
                "role", user.getRole()
        );
    }
}
