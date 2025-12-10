package com.example.billpro.model;

import com.example.billpro.model.HistoriqueClient;
import com.example.billpro.model.HistoriqueEmploye;
import com.example.billpro.model.HistoriqueFacture;
import com.example.billpro.model.HistoriqueProduit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employes")
@Getter
@Setter
public class Employe {


    @Id
    private String id_emp;

    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String tel;
    private String cin;
    private Boolean actif = true;

    @Temporal(TemporalType.DATE)
    private Date date_naissance;

    @Temporal(TemporalType.DATE)
    private Date date_embauche;

    @JsonIgnore
    @JsonIgnoreProperties({"employe"})
    @OneToMany(mappedBy = "employe")
    private List<HistoriqueEmploye> historiques = new ArrayList<>();

    @JsonIgnore
    @JsonIgnoreProperties({"employe"})
    @OneToMany(mappedBy = "employe")
    private List<HistoriqueClient> historiquesClients = new ArrayList<>();

    @JsonIgnore
    @JsonIgnoreProperties({"employe"})
    @OneToMany(mappedBy = "employe")
    private List<HistoriqueFacture> historiquesFactures = new ArrayList<>();

    @JsonIgnore
    @JsonIgnoreProperties({"employe"})
    @OneToMany(mappedBy = "employe")
    private List<HistoriqueProduit> historiquesProduits = new ArrayList<>();


    public Employe() {}


}