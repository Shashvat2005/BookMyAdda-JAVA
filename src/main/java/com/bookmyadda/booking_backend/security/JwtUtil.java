package com.bookmyadda.booking_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET =
            "THIS_IS_A_VERY_LONG_SECRET_KEY_FOR_HS256_DO_NOT_SHORTEN";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public static Claims extractClaims(String token) {

        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
