package com.example.billpro.controller.superadmin;

import com.example.billpro.model.Manager;
import com.example.billpro.service.superadmin.SuperAdminManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/superadmin/managers")
@CrossOrigin(origins = "*")
public class SuperAdminManagerController {

    @Autowired
    private SuperAdminManagerService managerService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> createManager(@RequestBody Manager manager) {
        try {
            Manager created = managerService.createManager(manager);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message",
                    "Erreur lors de la création du manager : " + e.getMessage()));
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateManager(
            @PathVariable("id") String idMan,
            @RequestBody Manager managerDetails) {
        try {
            Manager updated = managerService.updateManager(idMan, managerDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // DEACTIVATE
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateManager(@PathVariable("id") String idMan) {
        try {
            Manager deactivated = managerService.deactivateManager(idMan);
            return ResponseEntity.ok(Map.of(
                    "message", "Manager désactivé avec succès",
                    "managerId", deactivated.getId_man()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    //Activate
    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateManager(@PathVariable("id") String idMan) {
        try {
            Manager activated = managerService.activateManager(idMan);
            return ResponseEntity.ok(Map.of(
                    "message", "Manager activé avec succès",
                    "managerId", activated.getId_man()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Manager>> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    // GET WITH PAGINATION
    @GetMapping("/page")
    public ResponseEntity<Page<Manager>> getManagersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date_fct") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(managerService.getAllManagers(pageable));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable("id") String idMan) {
        try {
            Manager manager = managerService.getManagerById(idMan);
            return ResponseEntity.ok(manager);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
