package com.akash.event_service.auth.controller;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.event_service.auth.model.Role;
import com.akash.event_service.auth.model.User;
import com.akash.event_service.auth.repo.UserRepository;
import com.akash.event_service.auth.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String role = body.getOrDefault("role", "USER");

        if (userRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        User user = User.builder()
                .email(email)
                .password(encoder.encode(password))  // hash password
                .role(Role.valueOf(role.toUpperCase()))
                .build();

        return userRepo.save(user);
    }

    // LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());
        return Map.of(
                "token", token,
                "role", user.getRole().name()
        );
    }
}
