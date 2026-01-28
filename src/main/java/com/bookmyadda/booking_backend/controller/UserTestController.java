package com.bookmyadda.booking_backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserTestController {

    @GetMapping("/me")
    public String me(Authentication auth) {
        return "Hello " + auth.getName();
    }
}
