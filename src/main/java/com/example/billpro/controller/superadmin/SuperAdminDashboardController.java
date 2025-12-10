package com.example.billpro.controller.superadmin;

import com.example.billpro.service.superadmin.SuperAdminDashboardDto;
import com.example.billpro.service.superadmin.SuperAdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour le tableau de bord du SuperAdmin.
 * Fournit les statistiques globales du système.
 */
@RestController
@RequestMapping("/api/superadmin/dashboard")
@CrossOrigin(origins = "*")
public class SuperAdminDashboardController {

    @Autowired
    private SuperAdminDashboardService dashboardService;

    /**
     * GET /api/superadmin/dashboard/stats
     * Récupère les statistiques globales pour le tableau de bord du super admin
     *
     * Statistiques retournées:
     * - Nombre total de managers
     * - Nombre total d'employés
     * - Nombre total de clients
     * - Nombre total de produits
     * - Nombre total de factures
     * - Chiffre d'affaires total (somme des factures TTC)
     *
     * @return SuperAdminDashboardDto contenant toutes les statistiques
     */
    @GetMapping("/stats")
    public ResponseEntity<SuperAdminDashboardDto> getDashboardStats() {
        SuperAdminDashboardDto stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}