package com.example.billpro.service.superadmin;

import com.example.billpro.model.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SuperAdminManagerService {
    Manager createManager(Manager manager);
    Manager updateManager(String idMan, Manager managerDetails);
    Manager deactivateManager(String idMan);
    List<Manager> getAllManagers();
    Page<Manager> getAllManagers(Pageable pageable);
    Manager getManagerById(String idMan);
    Manager activateManager(String idMan);

}