package com.example.billpro.controller;

import com.example.billpro.dto.LoginRequest;
import com.example.billpro.dto.LoginResponse;
import com.example.billpro.model.Employe;
import com.example.billpro.model.Manager;
import com.example.billpro.model.SuperAdmin;
import com.example.billpro.repository.EmployeRepository;
import com.example.billpro.repository.ManagerRepository;
import com.example.billpro.repository.SuperAdminRepository;
import com.example.billpro.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Pour autoriser les appels depuis mobile
    public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private SuperAdminRepository superAdminRepo;
    @Autowired private ManagerRepository managerRepo;
    @Autowired private EmployeRepository employeRepo;

    /**
     * Endpoint de login : POST /api/auth/login
     * Body : { "email": "...", "password": "..." }
     * Retourne : { "token": "...", "role": "...", "nom": "...", "prenom": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // 1. Vérifie email + password via Spring Security
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            // 2. Cherche l'utilisateur dans les 3 tables pour récupérer nom/prénom/rôle
            SuperAdmin sa = superAdminRepo.findByEmail(request.email()).orElse(null);
            if (sa != null) {
                String token = jwtUtil.generateToken(sa.getEmail(), "SUPERADMIN", sa.getNom(), sa.getPrenom());
                return ResponseEntity.ok(new LoginResponse(token, "SUPERADMIN", sa.getNom(), sa.getPrenom()));
            }

            Manager m = managerRepo.findByEmail(request.email()).orElse(null);
            if (m != null && Boolean.TRUE.equals(m.getActif())) {
                String token = jwtUtil.generateToken(m.getEmail(), "MANAGER", m.getNom(), m.getPrenom());
                return ResponseEntity.ok(new LoginResponse(token, "MANAGER", m.getNom(), m.getPrenom()));
            }

            Employe e = employeRepo.findByEmail(request.email()).orElse(null);
            if (e != null && Boolean.TRUE.equals(e.getActif())) {
                String token = jwtUtil.generateToken(e.getEmail(), "EMPLOYE", e.getNom(), e.getPrenom());
                return ResponseEntity.ok(new LoginResponse(token, "EMPLOYE", e.getNom(), e.getPrenom()));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utilisateur inactif ou introuvable");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe incorrect");
        }
    }

    /**
     * Endpoint de test (optionnel)
     * GET /api/auth/test
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("API BillPro fonctionne !");
    }
}