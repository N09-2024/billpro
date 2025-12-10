package com.example.billpro.service.manager;

import com.example.billpro.model.Employe;
import com.example.billpro.model.HistoriqueEmploye;
import com.example.billpro.model.Manager;
import com.example.billpro.repository.EmployeRepository;
import com.example.billpro.repository.HistoriqueEmployeRepository;
import com.example.billpro.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ManagerEmployeServiceImpl implements ManagerEmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private HistoriqueEmployeRepository historiqueEmployeRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // =======================================================
    // MÉTHODE UTILITAIRE POUR RÉCUPÉRER LE MANAGER CONNECTÉ
    // (Inchangée)
    // =======================================================

    /**
     * Récupère l'entité Manager actuellement connectée via Spring Security.
     */
    private Manager getManagerFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        if (authentication.getPrincipal() instanceof Manager) {
            return (Manager) authentication.getPrincipal();
        }

        try {
            String managerEmail = authentication.getName();
            return managerRepository.findByEmail(managerEmail).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    // =======================================================
    // MÉTHODES D'ACTION (CREATE, UPDATE, DELETE, DEACTIVATE)
    // =======================================================

    @Override
    public Employe createEmploye(Employe employe) {
        // Récupérer le Manager connecté
        Manager manager = getManagerFromContext();

        // 💡 MODIFICATION : Générer un ID unique avec le préfixe "EMP"
        String idEmploye = "EMP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        employe.setId_emp(idEmploye);

        employe.setActif(true);
        employe.setDate_embauche(new Date());

        // Hachage du mot de passe
        if (employe.getMdp() != null && !employe.getMdp().trim().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(employe.getMdp());
            employe.setMdp(hashedPassword);
        }

        Employe saved = employeRepository.save(employe);

        // Historique création
        HistoriqueEmploye hist = new HistoriqueEmploye();
        hist.setId(UUID.randomUUID().toString());
        hist.setType_operation("CRÉATION");
        hist.setDate_operation(new Date());
        hist.setEmploye(saved);
        hist.setManager(manager);

        historiqueEmployeRepository.save(hist);

        return saved;
    }

    @Override
    public Employe updateEmploye(String idEmp, Employe employeDetails) {
        // Récupérer le Manager connecté
        Manager manager = getManagerFromContext();

        Employe employe = employeRepository.findById(idEmp)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        employe.setNom(employeDetails.getNom());
        employe.setPrenom(employeDetails.getPrenom());
        employe.setEmail(employeDetails.getEmail());
        employe.setTel(employeDetails.getTel());
        employe.setCin(employeDetails.getCin());
        employe.setDate_naissance(employeDetails.getDate_naissance());
        if (employeDetails.getActif() != null) {
            employe.setActif(employeDetails.getActif());
        }

        // ... (gestion du mot de passe)

        Employe updated = employeRepository.save(employe);

        HistoriqueEmploye hist = new HistoriqueEmploye();
        hist.setId(UUID.randomUUID().toString());
        hist.setType_operation("MODIFICATION");
        hist.setDate_operation(new Date());
        hist.setEmploye(updated);

        // Assigner le Manager
        hist.setManager(manager);

        historiqueEmployeRepository.save(hist);

        return updated;
    }


    //nv nour2

    // ----------------------------------------------------
// 💡 NOUVELLE MÉTHODE PATCH : Modification partielle
// ----------------------------------------------------
    @Override
    public Employe patchEmploye(String idEmp, Employe employeDetails) {
        // Récupérer le Manager connecté
        Manager manager = getManagerFromContext();

        Employe employe = employeRepository.findById(idEmp)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        boolean modificationEffectuee = false;

        // Appliquer les mises à jour SEULEMENT si la nouvelle valeur n'est pas NULL

        if (employeDetails.getNom() != null) {
            employe.setNom(employeDetails.getNom());
            modificationEffectuee = true;
        }
        if (employeDetails.getPrenom() != null) {
            employe.setPrenom(employeDetails.getPrenom());
            modificationEffectuee = true;
        }
        // L'email ne doit pas être null selon le modèle, mais on peut vérifier si la valeur est envoyée
        if (employeDetails.getEmail() != null && !employeDetails.getEmail().isEmpty()) {
            employe.setEmail(employeDetails.getEmail());
            modificationEffectuee = true;
        }
        if (employeDetails.getTel() != null) {
            employe.setTel(employeDetails.getTel());
            modificationEffectuee = true;
        }
        if (employeDetails.getCin() != null) {
            employe.setCin(employeDetails.getCin());
            modificationEffectuee = true;
        }
        if (employeDetails.getDate_naissance() != null) {
            employe.setDate_naissance(employeDetails.getDate_naissance());
            modificationEffectuee = true;
        }
        // Le statut ACTIF est un Boolean, vérifier s'il est envoyé (peut être true ou false)
        if (employeDetails.getActif() != null) {
            employe.setActif(employeDetails.getActif());
            modificationEffectuee = true;
        }

        // Mise à jour du mot de passe (si fourni)
        if (employeDetails.getMdp() != null && !employeDetails.getMdp().trim().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(employeDetails.getMdp());
            employe.setMdp(hashedPassword);
            modificationEffectuee = true;
        }

        // Si aucune modification n'a été demandée, on peut lancer une exception ou simplement retourner l'objet.
        if (!modificationEffectuee) {
            throw new RuntimeException("Aucun champ valide fourni pour la modification partielle.");
        }

        Employe updated = employeRepository.save(employe);

        // Historique
        HistoriqueEmploye hist = new HistoriqueEmploye();
        hist.setId(UUID.randomUUID().toString());
        // Type d'opération plus précis
        hist.setType_operation("MODIFICATION PARTIELLE");
        hist.setDate_operation(new Date());
        hist.setEmploye(updated);
        hist.setManager(manager);

        historiqueEmployeRepository.save(hist);

        return updated;
    }

    @Override
    public void deleteEmploye(String idEmp) {
        // Récupérer le Manager connecté
        Manager manager = getManagerFromContext();

        Employe employe = employeRepository.findById(idEmp)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        // 1. Supprimer TOUS les historiques liés à cet employé (approche pour contourner l'erreur JPA)
        List<HistoriqueEmploye> historiques = historiqueEmployeRepository.findByEmploye_IdEmp(idEmp);
        historiqueEmployeRepository.deleteAll(historiques);

        // 2. Créer UN DERNIER historique de suppression SANS référence à l'employé
        HistoriqueEmploye hist = new HistoriqueEmploye();
        hist.setId(UUID.randomUUID().toString());
        hist.setType_operation("SUPPRESSION");
        hist.setDate_operation(new Date());

        // Assigner le Manager
        hist.setManager(manager);

        // NE PAS mettre hist.setEmploye(employe) pour éviter la référence
        historiqueEmployeRepository.save(hist);

        // 3. Maintenant on peut supprimer l'employé en toute sécurité
        employeRepository.delete(employe);
    }

    @Override
    public Employe deactivateEmploye(String idEmp) {
        // Récupérer le Manager connecté
        Manager manager = getManagerFromContext();

        // 1. Trouver l'employé existant
        Employe employeToDeactivate = employeRepository.findById(idEmp)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        // 2. Vérifier s'il n'est pas déjà inactif
        if (Boolean.FALSE.equals(employeToDeactivate.getActif())) {
            throw new RuntimeException("L'employé est déjà inactif.");
        }

        // 3. Mettre l'attribut ACTIF à false (désactivation)
        employeToDeactivate.setActif(false);

        // 4. Sauvegarder la modification
        Employe deactivatedEmploye = employeRepository.save(employeToDeactivate);

        // 5. Créer l'historique de DÉSACTIVATION
        HistoriqueEmploye hist = new HistoriqueEmploye();
        hist.setId(UUID.randomUUID().toString());
        hist.setType_operation("DÉSACTIVATION");
        hist.setDate_operation(new Date());
        hist.setEmploye(deactivatedEmploye);

        // Assigner le Manager
        hist.setManager(manager);

        // 6. Sauvegarder l'historique
        historiqueEmployeRepository.save(hist);

        return deactivatedEmploye;
    }

    // =======================================================
    // MÉTHODES DE LECTURE (GETTERS)
    // =======================================================

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public Page<Employe> getAllEmployes(Pageable pageable) {
        return employeRepository.findAll(pageable);
    }

    @Override
    public Employe getEmployeById(String idEmp) {
        return employeRepository.findById(idEmp)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé : " + idEmp));
    }
}