package com.example.billpro.controller.manager;

import com.example.billpro.model.Employe;
import com.example.billpro.service.manager.ManagerEmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manager/employes")
@CrossOrigin(origins = "*")
public class ManagerEmployeController {

    @Autowired
    private ManagerEmployeService employeService;

    /**
     * POST /api/manager/employes
     * Créer un nouvel employé
     */
    @PostMapping
    public ResponseEntity<?> createEmploye(@RequestBody Employe employe) {
        try {
            Employe created = employeService.createEmploye(employe);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur lors de la création de l'employé: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * PUT /api/manager/employes/{id}
     * Mettre à jour un employé existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmploye(
            @PathVariable("id") String idEmp,
            @RequestBody Employe employeDetails) {
        try {
            Employe updated = employeService.updateEmploye(idEmp, employeDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur lors de la modification: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * DELETE /api/manager/employes/{id}
     * Supprimer un employé
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable("id") String idEmp) {
        try {
            employeService.deleteEmploye(idEmp);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employé supprimé avec succès");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur lors de la suppression: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
// desactivation
    // Dans ManagerEmployeController.java (à ajouter)

    /**
     * PUT /api/manager/employes/{id}/deactivate
     * Désactive un employé (soft delete) en mettant 'actif' à false.
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateEmploye(@PathVariable("id") String idEmp) {
        try {
            Employe deactivatedEmploye = employeService.deactivateEmploye(idEmp);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Employé désactivé avec succès.");
            response.put("employeId", deactivatedEmploye.getId_emp());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Gérer les cas d'employé non trouvé ou déjà inactif
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            // Gérer les erreurs serveur inattendues
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur lors de la désactivation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // fin desactivation
    /**
     * GET /api/manager/employes
     * Récupérer tous les employés (liste simple)
     */
    @GetMapping
    public ResponseEntity<List<Employe>> getAllEmployes() {
        List<Employe> employes = employeService.getAllEmployes();
        return ResponseEntity.ok(employes);
    }

    /**
     * GET /api/manager/employes/page
     * Récupérer tous les employés avec pagination
     * Paramètres: page, size, sortBy, sortDir
     */
    @GetMapping("/page")
    public ResponseEntity<Page<Employe>> getAllEmployesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date_embauche") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employe> employesPage = employeService.getAllEmployes(pageable);

        return ResponseEntity.ok(employesPage);
    }

    /**
     * GET /api/manager/employes/{id}
     * Récupérer un employé par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeById(@PathVariable("id") String idEmp) {
        try {
            Employe employe = employeService.getEmployeById(idEmp);
            return ResponseEntity.ok(employe);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}