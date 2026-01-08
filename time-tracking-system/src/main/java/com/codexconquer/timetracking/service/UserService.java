package com.codexconquer.timetracking.service;
import com.codexconquer.timetracking.exception.AuthException;
import com.codexconquer.timetracking.dto.LoginRequest;
import com.codexconquer.timetracking.dto.RegisterRequest;
import com.codexconquer.timetracking.entity.User;
import com.codexconquer.timetracking.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                // 🔐 ENCRYPT PASSWORD HERE
                .password(passwordEncoder.encode(request.getPassword()))
                .role("CANDIDATE")
                .build();

        return userRepository.save(user);
    }



    public User login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("Invalid email or password");
        }

        return user;
    }

}
