package com.example.project.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY; // Final field
    private final long EXPIRATION_TIME_IN_DAYS; // Final field

    // Constructor injection
    public JwtUtil(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.expiration-time-in-days}") long expirationTime) {
        this.SECRET_KEY = secretKey;
        this.EXPIRATION_TIME_IN_DAYS = expirationTime;
    }

    public String generateToken(String email) {
        long EXPIRY_TIME_IN_MILLI_SECONDS = EXPIRATION_TIME_IN_DAYS * 24 * 60 * 60 * 1000L;
        return Jwts.builder()
                .setSubject(email) // Set the subject (e.g., email)
                .setIssuedAt(new Date()) // Set the issue date
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME_IN_MILLI_SECONDS)) // Set expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token
                .compact(); // Build the token
    }


    // Method to verify the token and extract the subject (email)
    public String verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY) // Use the same secret key
                    .parseClaimsJws(token) // Parse the token
                    .getBody(); // Extract the claims

            // Return the subject (email) from the claims
            return claims.getSubject();
        } catch (Exception e) {
            // Handle invalid tokens (e.g., expired or tampered tokens)
            throw new RuntimeException("Invalid or expired token: " + e.getMessage());
        }
    }



}