package com.example.billpro.repository;

import com.example.billpro.model.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, String> {

    Optional<SuperAdmin> findByEmail(String email);
}