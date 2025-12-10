package com.example.billpro.dto;

public record LoginResponse(
        String token,        // JWT
        String role,         // "SUPERADMIN", "MANAGER" ou "EMPLOYE"
        String nom,          // ex: "DUPONT"
        String prenom        // ex: "Jean"
) {
}
