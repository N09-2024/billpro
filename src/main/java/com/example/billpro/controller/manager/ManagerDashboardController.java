package com.example.billpro.controller.manager;

import com.example.billpro.service.manager.ManagerDashboardDto;
import com.example.billpro.service.manager.ManagerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/dashboard")
@CrossOrigin(origins = "*")
public class ManagerDashboardController {

    @Autowired
    private ManagerDashboardService dashboardService;

    /**
     * GET /api/manager/dashboard/stats
     * Récupère les statistiques globales pour le tableau de bord du manager
     */
    @GetMapping("/stats")
    public ResponseEntity<ManagerDashboardDto> getDashboardStats() {
        ManagerDashboardDto stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
}