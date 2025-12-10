package com.example.billpro.repository;

import com.example.billpro.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository; // <-- Ajoute cet import (optionnel mais propre)
import org.springframework.data.repository.query.Param; // <-- AJOUTEZ CETTE LIGNE
import java.util.List;


import com.example.billpro.model.StatutFacture;



@Repository // Optionnel mais recommandé
public interface FactureRepository extends JpaRepository<Facture, String> {

    @Query("SELECT COALESCE(SUM(f.totalTTC), 0) FROM Facture f")
    Double sumTotalTTC();

    // Le nom de la méthode peut être simple, mais la requête HQL est précise.
    @Query("SELECT f FROM Facture f WHERE f.client.id_client = :clientId")
    List<Facture> findByClientId(@Param("clientId") String clientId);

    // nouveau safae auth


    // Factures par statut
    List<Facture> findByStatut(StatutFacture statut);

    // Factures d'un client avec un statut spécifique
    @Query("SELECT f FROM Facture f WHERE f.client.id_client = :clientId AND f.statut = :statut")
    List<Facture> findByClientIdAndStatut(
            @Param("clientId") String clientId,
            @Param("statut") StatutFacture statut
    );

    // Factures en retard
    @Query("SELECT f FROM Facture f WHERE f.statut = 'EN_RETARD'")
    List<Facture> findFacturesEnRetard();

    // Total des factures d'un client
    @Query("SELECT COALESCE(SUM(f.totalTTC), 0.0) FROM Facture f WHERE f.client.id_client = :clientId")
    Double getTotalFacturesClient(@Param("clientId") String clientId);

}