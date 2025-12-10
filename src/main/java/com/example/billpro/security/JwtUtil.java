// src/main/java/com/example/billpro/security/JwtUtil.java
package com.example.billpro.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Clé secrète (doit faire au moins 256 bits pour HS512)
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "BillProMobile2025SecretKeyUltraLongAndRandom12345678901234567890".getBytes()
    );

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 heures  duree de vie de token

    // Génère le token
    public String generateToken(String email, String role, String nom, String prenom) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .claim("nom", nom)
                .claim("prenom", prenom)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Récupère les claims (toutes les infos du token)
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}