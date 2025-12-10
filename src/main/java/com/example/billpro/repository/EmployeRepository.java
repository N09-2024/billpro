package com.example.billpro.repository;

import com.example.billpro.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, String> {

    Optional<Employe> findByEmail(String email);
}


