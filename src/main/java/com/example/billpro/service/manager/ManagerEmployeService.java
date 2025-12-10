package com.example.billpro.service.manager;

import com.example.billpro.model.Employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManagerEmployeService {
    Employe createEmploye(Employe employe);
    Employe updateEmploye(String idEmp, Employe employeDetails);
    // 💡 NOUVELLE MÉTHODE
    Employe patchEmploye(String idEmp, Employe employeDetails);
    void deleteEmploye(String idEmp);
    // desactivation

    Employe deactivateEmploye(String idEmp);
    List<Employe> getAllEmployes();
    Page<Employe> getAllEmployes(Pageable pageable);
    Employe getEmployeById(String idEmp);
}