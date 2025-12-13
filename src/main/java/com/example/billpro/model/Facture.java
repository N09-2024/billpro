package com.example.billpro.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factures")
@Getter
@Setter
public class Facture {

    @Id
    private String id_facture;

    @Temporal(TemporalType.DATE)
    private Date dateFacture;

    @Temporal(TemporalType.DATE)
    private Date dateEcheance;

    private Double totalHT;
    private Double totalTVA;
    private Double totalTTC;
    @Enumerated(EnumType.STRING)
    private StatutFacture statut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "facture")
    @JsonIgnore
    private List<Paiement> paiements = new ArrayList<>();

    @OneToMany(mappedBy = "facture")
    @JsonIgnore
    private List<HistoriqueFacture> historiquesEmployes = new ArrayList<>();

    @OneToMany(mappedBy = "facture")
    @JsonIgnore
    private List<LigneFacture> lignes = new ArrayList<>();

    public Facture() {
    }
}