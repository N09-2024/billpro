package com.example.billpro.controller.superadmin;

import com.example.billpro.model.Produit;
import com.example.billpro.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin/produits")
@CrossOrigin(origins = "*")
public class SuperAdminProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return ResponseEntity.ok(produitRepository.findAll());
    }
}
