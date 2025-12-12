package com.example.billpro.controller.superadmin;

import com.example.billpro.model.SuperAdmin;
import com.example.billpro.repository.SuperAdminRepository;
import com.example.billpro.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/superadmin")
@CrossOrigin(origins = "*")
public class SuperAdminProfileController {

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            SuperAdmin superAdmin = superAdminRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("SuperAdmin non trouvé"));

            Map<String, Object> response = new HashMap<>();
            response.put("id_admin", superAdmin.getId_admin());
            response.put("nom", superAdmin.getNom());
            response.put("prenom", superAdmin.getPrenom());
            response.put("email", superAdmin.getEmail());
            // Ajouter d'autres champs si nécessaires

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody SuperAdmin updatedProfile) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            SuperAdmin superAdmin = superAdminRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("SuperAdmin non trouvé"));

            // Mettre à jour les champs
            if (updatedProfile.getNom() != null) {
                superAdmin.setNom(updatedProfile.getNom());
            }
            if (updatedProfile.getPrenom() != null) {
                superAdmin.setPrenom(updatedProfile.getPrenom());
            }
            if (updatedProfile.getEmail() != null) {
                superAdmin.setEmail(updatedProfile.getEmail());
            }

            SuperAdmin saved = superAdminRepository.save(superAdmin);

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody com.example.billpro.dto.PasswordChangeRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            SuperAdmin superAdmin = superAdminRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("SuperAdmin non trouvé"));

            // Vérifier l'ancien mot de passe
            if (!passwordEncoder.matches(request.oldPassword(), superAdmin.getMdp())) {
                return ResponseEntity.status(401).body(Map.of("message", "Ancien mot de passe incorrect"));
            }

            // Mettre à jour avec le nouveau mot de passe
            superAdmin.setMdp(passwordEncoder.encode(request.newPassword()));
            superAdminRepository.save(superAdmin);

            return ResponseEntity.ok(Map.of("message", "Mot de passe mis à jour avec succès"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Erreur: " + e.getMessage()));
        }
    }
}