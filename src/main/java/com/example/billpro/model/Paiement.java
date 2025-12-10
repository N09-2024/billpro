package com.example.billpro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "paiements")
@Getter
@Setter
public class Paiement {

    @Id
    private String id_paiement;

    private Double montant;

    @Temporal(TemporalType.DATE)
    private Date datePaiement;

    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;

    public Paiement() {}
}