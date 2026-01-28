package com.bookmyadda.booking_backend.controller;

import com.bookmyadda.booking_backend.dto.ForgotPasswordRequestDTO;
import com.bookmyadda.booking_backend.dto.ResetPasswordRequestDTO;
import com.bookmyadda.booking_backend.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    private final PasswordResetService resetService;

    public PasswordResetController(PasswordResetService resetService) {
        this.resetService = resetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {

        resetService.generateResetToken(forgotPasswordRequestDTO.getEmail());
        return ResponseEntity.ok(
                "If the email exists, a reset link has been sent"
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {

        resetService.resetPassword(
                resetPasswordRequestDTO.getToken(),
                resetPasswordRequestDTO.getNewPassword()
        );

        return ResponseEntity.ok("Password reset successful");
    }
}
