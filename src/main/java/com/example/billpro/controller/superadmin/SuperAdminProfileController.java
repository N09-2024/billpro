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
}