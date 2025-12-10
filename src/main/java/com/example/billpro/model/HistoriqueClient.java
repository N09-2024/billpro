package com.example.billpro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "historique_client")
@Getter
@Setter
public class HistoriqueClient {

    @Id
    private String id;

    private String type_operation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_operation;


    @JsonIgnoreProperties({"historiques"})
    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;


    @JsonIgnoreProperties({"historiques"})
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public HistoriqueClient() {}
}