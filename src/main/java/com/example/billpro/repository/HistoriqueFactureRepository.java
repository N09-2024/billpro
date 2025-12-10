package com.example.billpro.repository;

import com.example.billpro.model.HistoriqueFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.billpro.model.HistoriqueProduit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository; // <-- Ajoute cet import (optionnel mais propre)
import org.springframework.data.repository.query.Param; // <-- AJOUTEZ CETTE LIGNE


public interface HistoriqueFactureRepository extends JpaRepository<HistoriqueFacture, String> {

    // NOUVEAU : Utilise une requête JPQL explicite pour trier par date_operation.
    @Query("SELECT h FROM HistoriqueFacture h ORDER BY h.date_operation DESC")
    Page<HistoriqueFacture> findAllSorted(Pageable pageable);

    @Query("SELECT h FROM HistoriqueFacture h WHERE h.employe.id_emp = :employeId ORDER BY h.date_operation DESC")
    Page<HistoriqueFacture> findByEmployeId(@Param("employeId") String employeId, Pageable pageable);
}