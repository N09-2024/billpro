package com.example.billpro.service.manager;

import com.example.billpro.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerHistoriqueService {

    // Toutes les actions (tous employés)
    Page<HistoriqueClient> getAllHistoriqueClients(Pageable pageable);
    Page<HistoriqueProduit> getAllHistoriqueProduits(Pageable pageable);
    Page<HistoriqueFacture> getAllHistoriqueFactures(Pageable pageable);

    // Actions par employé
    Page<HistoriqueClient> getHistoriqueClientsByEmploye(String employeId, Pageable pageable);
    Page<HistoriqueProduit> getHistoriqueProduitsByEmploye(String employeId, Pageable pageable);
    Page<HistoriqueFacture> getHistoriqueFacturesByEmploye(String employeId, Pageable pageable);
}