package com.example.billpro.repository;

import com.example.billpro.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.billpro.model.ModePaiement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface PaiementRepository extends JpaRepository<Paiement, String> {
    @Query("SELECT p FROM Paiement p WHERE p.facture.id_facture = :factureId")
    List<Paiement> findByFactureId(@Param("factureId") String factureId);

    //nouveaus safae

    // Paiements par mode
    List<Paiement> findByModePaiement(ModePaiement modePaiement);

    // Total payé pour une facture
    @Query("SELECT COALESCE(SUM(p.montant), 0.0) FROM Paiement p WHERE p.facture.id_facture = :factureId")
    Double getTotalPaiementsFacture(@Param("factureId") String factureId);
}