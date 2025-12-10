package com.example.billpro.service.manager;

import com.example.billpro.repository.*;
import com.example.billpro.service.manager.ManagerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ManagerDashboardServiceImpl implements ManagerDashboardService {

    @Autowired
    private EmployeRepository employeRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ProduitRepository produitRepo;

    @Autowired
    private FactureRepository factureRepo;

    @Override
    public ManagerDashboardDto getDashboardStats() {
        ManagerDashboardDto dto = new ManagerDashboardDto();

        dto.setTotalEmployes(employeRepo.count());
        dto.setTotalClients(clientRepo.count());
        dto.setTotalProduits(produitRepo.count());
        dto.setTotalFactures(factureRepo.count());

        Double ca = factureRepo.sumTotalTTC();
        dto.setChiffreAffairesTotal(ca != null ? ca : 0.0);

        return dto;
    }
}