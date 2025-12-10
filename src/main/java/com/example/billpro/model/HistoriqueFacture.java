package com.example.billpro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "historique_facture")
@Getter
@Setter
public class HistoriqueFacture {

    @Id
    private String id;

    private String type_operation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_operation;


    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;


    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;

    public HistoriqueFacture() {}
}