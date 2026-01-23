package com.codexconquer.timetracking.controller;

import com.codexconquer.timetracking.dto.RegisterRequest;
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

    @PostMapping("/register")
    public User register(@RequestBody @Valid RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {

        User user = userService.login(request);
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        return Map.of(
                "token", token,
                "user", user
        );
    }


}
