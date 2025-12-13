package com.example.billpro.model;

import com.example.billpro.model.CategorieProduit;
import com.example.billpro.model.HistoriqueProduit;
import com.example.billpro.model.LigneFacture;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produits")
@Getter
@Setter
public class Produit {

    @Id
    private String id_produit;

    private String nom;
    private String description;
    private Double prixHT;
    private Double TVA;
    private Integer stock; // quantite
    private Boolean actif = true;

    @Enumerated(EnumType.STRING)
    private CategorieProduit categorie;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    private List<HistoriqueProduit> historiquesEmployes = new ArrayList<>();

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    private List<LigneFacture> lignesFacture = new ArrayList<>();

    public Produit() {
    }
}