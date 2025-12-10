package com.example.billpro.service.manager;


import com.example.billpro.repository.*;
import com.example.billpro.service.manager.ManagerHistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.billpro.model.HistoriqueClient;
import com.example.billpro.model.HistoriqueProduit;
import com.example.billpro.model.HistoriqueFacture;
import com.example.billpro.repository.*;
import com.example.billpro.service.manager.ManagerHistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ManagerHistoriqueServiceImpl implements ManagerHistoriqueService {

    @Autowired
    private HistoriqueClientRepository histClientRepo;

    @Autowired
    private HistoriqueProduitRepository histProduitRepo;

    @Autowired
    private HistoriqueFactureRepository histFactureRepo;

    @Override
    public Page<HistoriqueClient> getAllHistoriqueClients(Pageable p) {
        return histClientRepo.findAllSorted(p);
    }

    @Override
    public Page<HistoriqueProduit> getAllHistoriqueProduits(Pageable p) {
        return histProduitRepo.findAllSorted(p);
    }

    @Override
    public Page<HistoriqueFacture> getAllHistoriqueFactures(Pageable p) {
        return histFactureRepo.findAllSorted(p);
    }

    @Override
    public Page<HistoriqueClient> getHistoriqueClientsByEmploye(String id, Pageable p) {
        return histClientRepo.findByEmployeId(id, p);
    }

    @Override
    public Page<HistoriqueProduit> getHistoriqueProduitsByEmploye(String id, Pageable p) {
        return histProduitRepo.findByEmployeId(id, p);
    }

    @Override
    public Page<HistoriqueFacture> getHistoriqueFacturesByEmploye(String id, Pageable p) {
        return histFactureRepo.findByEmployeId(id, p);
    }
}