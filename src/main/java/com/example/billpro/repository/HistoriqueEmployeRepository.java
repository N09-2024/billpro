package com.example.billpro.repository;

import com.example.billpro.model.HistoriqueEmploye;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // <-- AJOUTEZ CETTE LIGNE

import java.util.List;

public interface HistoriqueEmployeRepository extends JpaRepository<HistoriqueEmploye, String> {
    // Méthode pour trouver tous les historiques d'un employé
    @Query("SELECT h FROM HistoriqueEmploye h WHERE h.employe.id_emp = :idEmp")
    List<HistoriqueEmploye> findByEmploye_IdEmp(@Param("idEmp") String idEmp);

    // Méthode pour la pagination triée par date décroissante
    @Query("SELECT h FROM HistoriqueEmploye h ORDER BY h.date_operation DESC")
    Page<HistoriqueEmploye> findAllByOrderByDate_operationDesc(Pageable pageable);

    //superAdmin
    @Query("SELECT h FROM HistoriqueEmploye h WHERE h.manager.id_man = :idMan")
    List<HistoriqueEmploye> findByManager_IdMan(@Param("idMan") String idMan);
}