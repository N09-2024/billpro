package com.example.billpro.repository;

import com.example.billpro.model.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneFactureRepository extends JpaRepository<LigneFacture, String> {
}