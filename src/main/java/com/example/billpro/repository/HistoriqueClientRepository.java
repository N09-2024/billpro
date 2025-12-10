package com.example.billpro.repository;

import com.example.billpro.model.HistoriqueClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository; // <-- Ajoute cet import (optionnel mais propre)
import org.springframework.data.repository.query.Param; // <-- AJOUTEZ CETTE LIGNE


public interface HistoriqueClientRepository extends JpaRepository<HistoriqueClient, String> {

    // NOUVEAU : Utilise une requête JPQL explicite pour trier par date_operation.
    @Query("SELECT h FROM HistoriqueClient h ORDER BY h.date_operation DESC")
    Page<HistoriqueClient> findAllSorted(Pageable pageable);

    @Query("SELECT h FROM HistoriqueClient h WHERE h.employe.id_emp = :employeId ORDER BY h.date_operation DESC")
    Page<HistoriqueClient> findByEmployeId(@Param("employeId") String employeId, Pageable pageable);

}