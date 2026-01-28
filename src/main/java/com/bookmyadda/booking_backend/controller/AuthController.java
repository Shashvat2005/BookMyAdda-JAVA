package com.bookmyadda.booking_backend.controller;

import com.bookmyadda.booking_backend.dto.LoginRequestDTO;
import com.bookmyadda.booking_backend.dto.RegisterRequestDTO;
import com.bookmyadda.booking_backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        authService.register(registerRequestDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(token);
    }
}
