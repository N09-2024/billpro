package com.example.billpro.controller.superadmin;

import com.example.billpro.model.*;
import com.example.billpro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/superadmin/historique")
@CrossOrigin(origins = "*")
public class SuperAdminHistoriqueController {

    @Autowired
    private HistoriqueEmployeRepository historiqueEmployeRepo;

    @Autowired
    private HistoriqueClientRepository historiqueClientRepo;

    @Autowired
    private HistoriqueProduitRepository historiqueProduitRepo;

    @Autowired
    private HistoriqueFactureRepository historiqueFactureRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private EmployeRepository employeRepo;

    /**
     * GET /api/superadmin/historique/system
     * Récupère l'historique système complet (toutes les actions)
     */
    @GetMapping("/system")
    public ResponseEntity<?> getSystemHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("date_operation").descending());

            // Récupérer les historiques d'employés (créés par les managers)
            Page<HistoriqueEmploye> histEmployes = historiqueEmployeRepo.findAllByOrderByDate_operationDesc(pageable);

            // Convertir en format commun
            List<Map<String, Object>> allHistory = histEmployes.getContent().stream()
                    .map(this::convertHistoriqueEmployeToMap)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("content", allHistory);
            response.put("totalElements", histEmployes.getTotalElements());
            response.put("totalPages", histEmployes.getTotalPages());
            response.put("page", page);
            response.put("size", size);
            response.put("totalItems", allHistory.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la récupération de l'historique: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * GET /api/superadmin/historique/actions/employes
     * Historique des actions sur les employés
     */
    @GetMapping("/actions/employes")
    public ResponseEntity<?> getEmployeActionsHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("date_operation").descending());
            Page<HistoriqueEmploye> histPage = historiqueEmployeRepo.findAllByOrderByDate_operationDesc(pageable);

            List<Map<String, Object>> historyItems = histPage.getContent().stream()
                    .map(this::convertHistoriqueEmployeToMap)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("content", historyItems);
            response.put("totalElements", histPage.getTotalElements());
            response.put("totalPages", histPage.getTotalPages());
            response.put("page", page);
            response.put("size", size);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * GET /api/superadmin/historique/actions/clients
     * Historique des actions sur les clients
     */
    @GetMapping("/actions/clients")
    public ResponseEntity<?> getClientActionsHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("date_operation").descending());
            Page<HistoriqueClient> histPage = historiqueClientRepo.findAllSorted(pageable);

            List<Map<String, Object>> historyItems = histPage.getContent().stream()
                    .map(this::convertHistoriqueClientToMap)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("content", historyItems);
            response.put("totalElements", histPage.getTotalElements());
            response.put("totalPages", histPage.getTotalPages());
            response.put("page", page);
            response.put("size", size);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * GET /api/superadmin/historique/actions/produits
     * Historique des actions sur les produits
     */
    @GetMapping("/actions/produits")
    public ResponseEntity<?> getProduitActionsHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("date_operation").descending());
            Page<HistoriqueProduit> histPage = historiqueProduitRepo.findAllSorted(pageable);

            List<Map<String, Object>> historyItems = histPage.getContent().stream()
                    .map(this::convertHistoriqueProduitToMap)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("content", historyItems);
            response.put("totalElements", histPage.getTotalElements());
            response.put("totalPages", histPage.getTotalPages());
            response.put("page", page);
            response.put("size", size);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * GET /api/superadmin/historique/actions/factures
     * Historique des actions sur les factures
     */
    @GetMapping("/actions/factures")
    public ResponseEntity<?> getFactureActionsHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("date_operation").descending());
            Page<HistoriqueFacture> histPage = historiqueFactureRepo.findAllSorted(pageable);

            List<Map<String, Object>> historyItems = histPage.getContent().stream()
                    .map(this::convertHistoriqueFactureToMap)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("content", historyItems);
            response.put("totalElements", histPage.getTotalElements());
            response.put("totalPages", histPage.getTotalPages());
            response.put("page", page);
            response.put("size", size);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    /**
     * GET /api/superadmin/historique/manager/{managerId}
     * Historique des actions d'un manager spécifique
     */
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<?> getManagerHistory(
            @PathVariable String managerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        try {
            // Vérifier si le manager existe
            Optional<Manager> managerOpt = managerRepo.findById(managerId);
            if (managerOpt.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Manager non trouvé");
                return ResponseEntity.status(404).body(error);
            }

            Manager manager = managerOpt.get();

            // Récupérer les historiques d'employés créés par ce manager
            Pageable pageable = PageRequest.of(page, size, Sort.by("date_operation").descending());

            // Note: Si votre repository n'a pas la méthode findByManagerId, utilisez celle-ci :
            Page<HistoriqueEmploye> allHist = historiqueEmployeRepo.findAllByOrderByDate_operationDesc(pageable);

            List<HistoriqueEmploye> managerHist = allHist.getContent().stream()
                    .filter(h -> h.getManager() != null && h.getManager().getId_man().equals(managerId))
                    .collect(Collectors.toList());

            List<Map<String, Object>> historyItems = managerHist.stream()
                    .map(this::convertHistoriqueEmployeToMap)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("manager_nom", manager.getNom());
            response.put("manager_prenom", manager.getPrenom());
            response.put("manager_email", manager.getEmail());
            response.put("content", historyItems);
            response.put("totalElements", (long) managerHist.size());
            response.put("totalPages", 1);
            response.put("page", page);
            response.put("size", size);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }


    // ========== MÉTHODES DE CONVERSION ==========

    private Map<String, Object> convertHistoriqueEmployeToMap(HistoriqueEmploye h) {
        Map<String, Object> item = new HashMap<>();

        item.put("id", h.getId());
        item.put("type_operation", h.getType_operation());
        item.put("date_operation", h.getDate_operation());

        // Manager qui a effectué l'action
        if (h.getManager() != null) {
            item.put("actor_type", "MANAGER");
            item.put("actor_id", h.getManager().getId_man());
            item.put("actor_nom", h.getManager().getNom());
            item.put("actor_prenom", h.getManager().getPrenom());
            item.put("actor_email", h.getManager().getEmail());
            item.put("actor_name", h.getManager().getPrenom() + " " + h.getManager().getNom());
        } else {
            item.put("actor_type", "SYSTEM");
            item.put("actor_name", "Système");
        }

        // Cible (employé)
        if (h.getEmploye() != null) {
            item.put("target_type", "EMPLOYE");
            item.put("target_id", h.getEmploye().getId_emp());
            item.put("target_nom", h.getEmploye().getNom());
            item.put("target_prenom", h.getEmploye().getPrenom());
            item.put("target_email", h.getEmploye().getEmail());
            item.put("target_name", h.getEmploye().getPrenom() + " " + h.getEmploye().getNom());
        } else {
            item.put("target_type", "EMPLOYE");
            item.put("target_name", "Employé (supprimé)");
        }

        item.put("details", "Action sur employé: " + h.getType_operation());

        return item;
    }

    private Map<String, Object> convertHistoriqueClientToMap(HistoriqueClient h) {
        Map<String, Object> item = new HashMap<>();

        item.put("id", h.getId());
        item.put("type_operation", h.getType_operation());
        item.put("date_operation", h.getDate_operation());

        // Employé qui a effectué l'action
        if (h.getEmploye() != null) {
            item.put("actor_type", "EMPLOYE");
            item.put("actor_id", h.getEmploye().getId_emp());
            item.put("actor_nom", h.getEmploye().getNom());
            item.put("actor_prenom", h.getEmploye().getPrenom());
            item.put("actor_email", h.getEmploye().getEmail());
            item.put("actor_name", h.getEmploye().getPrenom() + " " + h.getEmploye().getNom());
        } else {
            item.put("actor_type", "SYSTEM");
            item.put("actor_name", "Système");
        }

        // Cible (client)
        if (h.getClient() != null) {
            item.put("target_type", "CLIENT");
            item.put("target_id", h.getClient().getId_client());
            item.put("target_nom", h.getClient().getNom());
            item.put("target_prenom", h.getClient().getPrenom());
            item.put("target_email", h.getClient().getEmail());
            item.put("target_name", h.getClient().getPrenom() + " " + h.getClient().getNom());
        } else {
            item.put("target_type", "CLIENT");
            item.put("target_name", "Client (supprimé)");
        }

        item.put("details", "Action sur client: " + h.getType_operation());

        return item;
    }

    private Map<String, Object> convertHistoriqueProduitToMap(HistoriqueProduit h) {
        Map<String, Object> item = new HashMap<>();

        item.put("id", h.getId());
        item.put("type_operation", h.getType_operation());
        item.put("date_operation", h.getDate_operation());

        // Employé qui a effectué l'action
        if (h.getEmploye() != null) {
            item.put("actor_type", "EMPLOYE");
            item.put("actor_id", h.getEmploye().getId_emp());
            item.put("actor_nom", h.getEmploye().getNom());
            item.put("actor_prenom", h.getEmploye().getPrenom());
            item.put("actor_email", h.getEmploye().getEmail());
            item.put("actor_name", h.getEmploye().getPrenom() + " " + h.getEmploye().getNom());
        } else {
            item.put("actor_type", "SYSTEM");
            item.put("actor_name", "Système");
        }

        // Cible (produit)
        if (h.getProduit() != null) {
            item.put("target_type", "PRODUIT");
            item.put("target_id", h.getProduit().getId_produit());
            item.put("target_nom", h.getProduit().getNom());
            item.put("target_name", h.getProduit().getNom());
            item.put("target_description", h.getProduit().getDescription());
        } else {
            item.put("target_type", "PRODUIT");
            item.put("target_name", "Produit (supprimé)");
        }

        item.put("details", "Action sur produit: " + h.getType_operation());

        return item;
    }

    private Map<String, Object> convertHistoriqueFactureToMap(HistoriqueFacture h) {
        Map<String, Object> item = new HashMap<>();

        item.put("id", h.getId());
        item.put("type_operation", h.getType_operation());
        item.put("date_operation", h.getDate_operation());

        // Employé qui a effectué l'action
        if (h.getEmploye() != null) {
            item.put("actor_type", "EMPLOYE");
            item.put("actor_id", h.getEmploye().getId_emp());
            item.put("actor_nom", h.getEmploye().getNom());
            item.put("actor_prenom", h.getEmploye().getPrenom());
            item.put("actor_email", h.getEmploye().getEmail());
            item.put("actor_name", h.getEmploye().getPrenom() + " " + h.getEmploye().getNom());
        } else {
            item.put("actor_type", "SYSTEM");
            item.put("actor_name", "Système");
        }

        // Cible (facture)
        if (h.getFacture() != null) {
            item.put("target_type", "FACTURE");
            item.put("target_id", h.getFacture().getId_facture());
            item.put("target_name", "Facture #" + h.getFacture().getId_facture());
            if (h.getFacture().getClient() != null) {
                item.put("client_name", h.getFacture().getClient().getPrenom() + " " + h.getFacture().getClient().getNom());
            }
        } else {
            item.put("target_type", "FACTURE");
            item.put("target_name", "Facture (supprimée)");
        }

        item.put("details", "Action sur facture: " + h.getType_operation());

        return item;
    }
}