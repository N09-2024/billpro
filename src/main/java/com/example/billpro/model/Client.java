package com.example.billpro.model;


import com.example.billpro.model.Facture;
import com.example.billpro.model.HistoriqueClient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {

    @Id
    private String id_client;

    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String adresse;
    private String cin;


    @OneToMany(mappedBy = "client")
    private List<HistoriqueClient> historiquesEmployes = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<Facture> factures = new ArrayList<>();

    public Client() {}
}
