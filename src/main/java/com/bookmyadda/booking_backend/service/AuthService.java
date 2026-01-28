package com.bookmyadda.booking_backend.service;

import com.bookmyadda.booking_backend.dto.LoginRequestDTO;
import com.bookmyadda.booking_backend.dto.RegisterRequestDTO;
import com.bookmyadda.booking_backend.entity.Role;
import com.bookmyadda.booking_backend.entity.User;
import com.bookmyadda.booking_backend.repository.UserRepository;
import com.bookmyadda.booking_backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequestDTO registerRequestDTO){
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    public String login(LoginRequestDTO loginRequestDTO) {

        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return JwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
    }
}

