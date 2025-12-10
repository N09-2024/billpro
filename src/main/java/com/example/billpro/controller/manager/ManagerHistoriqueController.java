package com.example.billpro.controller.manager;

import com.example.billpro.model.HistoriqueClient;
import com.example.billpro.model.HistoriqueFacture;
import com.example.billpro.model.HistoriqueProduit;
import com.example.billpro.service.manager.ManagerHistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/historiques")
@CrossOrigin(origins = "*")
public class ManagerHistoriqueController {

    @Autowired
    private ManagerHistoriqueService historiqueService;

    // ========== HISTORIQUES CLIENTS ==========

    /**
     * GET /api/manager/historiques/clients
     * Récupère tous les historiques clients (toutes actions, tous employés)
     * Paramètres: page, size
     */
    @GetMapping("/clients")
    public ResponseEntity<Page<HistoriqueClient>> getAllHistoriqueClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HistoriqueClient> historiques = historiqueService.getAllHistoriqueClients(pageable);
        return ResponseEntity.ok(historiques);
    }

    /**
     * GET /api/manager/historiques/clients/employe/{employeId}
     * Récupère les historiques clients d'un employé spécifique
     */
    @GetMapping("/clients/employe/{employeId}")
    public ResponseEntity<Page<HistoriqueClient>> getHistoriqueClientsByEmploye(
            @PathVariable String employeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HistoriqueClient> historiques =
                historiqueService.getHistoriqueClientsByEmploye(employeId, pageable);
        return ResponseEntity.ok(historiques);
    }

    // ========== HISTORIQUES PRODUITS ==========

    /**
     * GET /api/manager/historiques/produits
     * Récupère tous les historiques produits (toutes actions, tous employés)
     */
    @GetMapping("/produits")
    public ResponseEntity<Page<HistoriqueProduit>> getAllHistoriqueProduits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HistoriqueProduit> historiques = historiqueService.getAllHistoriqueProduits(pageable);
        return ResponseEntity.ok(historiques);
    }

    /**
     * GET /api/manager/historiques/produits/employe/{employeId}
     * Récupère les historiques produits d'un employé spécifique
     */
    @GetMapping("/produits/employe/{employeId}")
    public ResponseEntity<Page<HistoriqueProduit>> getHistoriqueProduitsByEmploye(
            @PathVariable String employeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HistoriqueProduit> historiques =
                historiqueService.getHistoriqueProduitsByEmploye(employeId, pageable);
        return ResponseEntity.ok(historiques);
    }

    // ========== HISTORIQUES FACTURES ==========

    /**
     * GET /api/manager/historiques/factures
     * Récupère tous les historiques factures (toutes actions, tous employés)
     */
    @GetMapping("/factures")
    public ResponseEntity<Page<HistoriqueFacture>> getAllHistoriqueFactures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HistoriqueFacture> historiques = historiqueService.getAllHistoriqueFactures(pageable);
        return ResponseEntity.ok(historiques);
    }

    /**
     * GET /api/manager/historiques/factures/employe/{employeId}
     * Récupère les historiques factures d'un employé spécifique
     */
    @GetMapping("/factures/employe/{employeId}")
    public ResponseEntity<Page<HistoriqueFacture>> getHistoriqueFacturesByEmploye(
            @PathVariable String employeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<HistoriqueFacture> historiques =
                historiqueService.getHistoriqueFacturesByEmploye(employeId, pageable);
        return ResponseEntity.ok(historiques);
    }
}