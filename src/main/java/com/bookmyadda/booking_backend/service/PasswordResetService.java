package com.bookmyadda.booking_backend.service;

import com.bookmyadda.booking_backend.entity.PasswordResetToken;
import com.bookmyadda.booking_backend.entity.User;
import com.bookmyadda.booking_backend.repository.PasswordResetTokenRepository;
import com.bookmyadda.booking_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(
            UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void generateResetToken(String email) {

        userRepository.findByEmail(email).ifPresent(user -> {

            PasswordResetToken token = new PasswordResetToken();
            token.setEmail(email);
            token.setToken(UUID.randomUUID().toString());
            token.setExpiresAt(LocalDateTime.now().plusMinutes(30));
            token.setUsed(false);

            tokenRepository.save(token);

            // TEMP: log instead of sending email
            System.out.println(
                    "Password reset link: " +
                            "http://localhost:3000/reset-password?token=" +
                            token.getToken()
            );
        });
    }

    public void resetPassword(String tokenValue, String newPassword) {

        PasswordResetToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (token.isUsed()) {
            throw new RuntimeException("Token already used");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findByEmail(token.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);
    }

}
