package com.bookmyadda.booking_backend.entity;

import jakarta.persistence.*;
import org.springframework.aot.generate.GeneratedTypeReference;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
}
