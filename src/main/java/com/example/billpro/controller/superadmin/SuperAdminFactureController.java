package com.example.billpro.controller.superadmin;

import com.example.billpro.model.Facture;
import com.example.billpro.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin/factures")
@CrossOrigin(origins = "*")
public class SuperAdminFactureController {

    @Autowired
    private FactureRepository factureRepository;

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        return ResponseEntity.ok(factureRepository.findAll());
    }
}
