package com.example.billpro.service.superadmin;

import com.example.billpro.model.Manager;
import com.example.billpro.model.SuperAdmin;
import com.example.billpro.repository.ManagerRepository;
import com.example.billpro.repository.SuperAdminRepository;
import com.example.billpro.repository.HistoriqueEmployeRepository;
import com.example.billpro.model.HistoriqueEmploye;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SuperAdminManagerServiceImpl implements SuperAdminManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private HistoriqueEmployeRepository historiqueEmployeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Récupère l'entité SuperAdmin actuellement connectée via Spring Security.
     */
    private SuperAdmin getSuperAdminFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }

        if (authentication.getPrincipal() instanceof SuperAdmin) {
            return (SuperAdmin) authentication.getPrincipal();
        }

        try {
            String superAdminEmail = authentication.getName();
            return superAdminRepository.findByEmail(superAdminEmail).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Manager createManager(Manager manager) {
        SuperAdmin superAdmin = getSuperAdminFromContext();

        manager.setId_man(UUID.randomUUID().toString());
        manager.setActif(true);
        manager.setDate_fct(new Date());

        // Hachage du mot de passe
        if (manager.getMdp() != null && !manager.getMdp().trim().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(manager.getMdp());
            manager.setMdp(hashedPassword);
        }

        // Associer le SuperAdmin au Manager
        if (superAdmin != null) {
            manager.setSuperAdmin(superAdmin);
        }

        Manager saved = managerRepository.save(manager);

        return saved;
    }

    @Override
    public Manager updateManager(String idMan, Manager managerDetails) {
        SuperAdmin superAdmin = getSuperAdminFromContext();

        Manager manager = managerRepository.findById(idMan)
                .orElseThrow(() -> new RuntimeException("Manager non trouvé"));

        manager.setNom(managerDetails.getNom());
        manager.setPrenom(managerDetails.getPrenom());
        manager.setEmail(managerDetails.getEmail());
        manager.setTel(managerDetails.getTel());
        manager.setCin(managerDetails.getCin());
        manager.setDate_naissance(managerDetails.getDate_naissance());

        if (managerDetails.getActif() != null) {
            manager.setActif(managerDetails.getActif());
        }

        // Mise à jour du mot de passe si fourni
        if (managerDetails.getMdp() != null && !managerDetails.getMdp().trim().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(managerDetails.getMdp());
            manager.setMdp(hashedPassword);
        }

        Manager updated = managerRepository.save(manager);

        return updated;
    }


    @Override
    public Manager deactivateManager(String idMan) {
        SuperAdmin superAdmin = getSuperAdminFromContext();

        Manager managerToDeactivate = managerRepository.findById(idMan)
                .orElseThrow(() -> new RuntimeException("Manager non trouvé"));

        if (Boolean.FALSE.equals(managerToDeactivate.getActif())) {
            throw new RuntimeException("Le manager est déjà inactif.");
        }

        managerToDeactivate.setActif(false);
        Manager deactivatedManager = managerRepository.save(managerToDeactivate);

        return deactivatedManager;
    }

    @Override
    public Manager activateManager(String idMan) {
        SuperAdmin superAdmin = getSuperAdminFromContext();

        Manager managerToActivate = managerRepository.findById(idMan)
                .orElseThrow(() -> new RuntimeException("Manager non trouvé"));

        if (Boolean.TRUE.equals(managerToActivate.getActif())) {
            throw new RuntimeException("Le manager est déjà actif.");
        }

        managerToActivate.setActif(true);
        Manager activatedManager = managerRepository.save(managerToActivate);

        return activatedManager;
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public Page<Manager> getAllManagers(Pageable pageable) {
        return managerRepository.findAll(pageable);
    }

    @Override
    public Manager getManagerById(String idMan) {
        return managerRepository.findById(idMan)
                .orElseThrow(() -> new RuntimeException("Manager non trouvé : " + idMan));
    }
}