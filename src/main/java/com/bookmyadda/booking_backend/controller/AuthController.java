package com.bookmyadda.booking_backend.controller;

import com.bookmyadda.booking_backend.dto.LoginRequestDTO;
import com.bookmyadda.booking_backend.dto.RegisterRequestDTO;
import com.bookmyadda.booking_backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @RequestBody RegisterRequestDTO registerRequestDTO
    ) {
        try {
            authService.register(registerRequestDTO);

            return ResponseEntity.ok(
                    Map.of(
                            "message", "User registered successfully"
                    )
            );

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(
                            Map.of(
                                    "error", e.getMessage()
                            )
                    );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            String token = authService.login(loginRequestDTO);
            return ResponseEntity.ok(
                    Map.of(
                            "token", token
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
