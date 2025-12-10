package com.example.billpro.repository;

import com.example.billpro.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, String> {

    Optional<Manager> findByEmail(String email);
}