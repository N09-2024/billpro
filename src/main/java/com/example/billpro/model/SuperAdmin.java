package com.example.billpro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "super_admins")
@Getter
@Setter
public class SuperAdmin {

    @Id
    private String id_admin;

    private String nom;
    private String prenom;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String mdp;

    @OneToMany(mappedBy = "superAdmin")
    private List<Manager> managers = new ArrayList<>();

    public SuperAdmin() {}

}