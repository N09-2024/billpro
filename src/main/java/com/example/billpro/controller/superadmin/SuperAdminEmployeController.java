package com.example.billpro.controller.superadmin;

import com.example.billpro.model.Employe;
import com.example.billpro.service.manager.ManagerEmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin/employes")
@CrossOrigin(origins = "*")
public class SuperAdminEmployeController {

    @Autowired
    private ManagerEmployeService employeService;

    /**
     * GET /api/superadmin/employes
     * Récupérer tous les employés (Read-Only pour SuperAdmin)
     */
    @GetMapping
    public ResponseEntity<List<Employe>> getAllEmployes() {
        List<Employe> employes = employeService.getAllEmployes();
        return ResponseEntity.ok(employes);
    }
}
