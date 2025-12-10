package com.example.billpro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ligne_facture")
@Getter
@Setter
public class LigneFacture {

    @Id
    private String id_ligne;

    private Integer quantite;
    private Double prix_unitaire;


    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;



    public LigneFacture() {}
}