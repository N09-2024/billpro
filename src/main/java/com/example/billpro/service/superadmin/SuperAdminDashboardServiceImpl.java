package com.example.billpro.service.superadmin;

import com.example.billpro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SuperAdminDashboardServiceImpl implements SuperAdminDashboardService {

    @Autowired private ManagerRepository managerRepo;
    @Autowired private EmployeRepository employeRepo;
    @Autowired private ClientRepository clientRepo;
    @Autowired private ProduitRepository produitRepo;
    @Autowired private FactureRepository factureRepo;

    @Override
    public SuperAdminDashboardDto getDashboardStats() {

        SuperAdminDashboardDto dto = new SuperAdminDashboardDto();

        dto.setTotalManagers(managerRepo.count());
        dto.setTotalEmployes(employeRepo.count());
        dto.setTotalClients(clientRepo.count());
        dto.setTotalProduits(produitRepo.count());
        dto.setTotalFactures(factureRepo.count());

        Double totalCA = factureRepo.sumTotalTTC();
        dto.setChiffreAffairesTotal(totalCA != null ? totalCA : 0.0);

        return dto;
    }
}
