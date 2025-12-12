package com.example.billpro.model;

import com.example.billpro.model.HistoriqueEmploye;
import com.example.billpro.model.SuperAdmin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "managers")
@Getter
@Setter
public class Manager {

    // === ATTRIBUTS DE BASE ===
    @Id
    private String id_man;

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
    private Date date_fct;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "super_admin_id")
    private SuperAdmin superAdmin;

    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    private List<HistoriqueEmploye> historiquesEmployes = new ArrayList<>();

    public Manager() {
    }

}