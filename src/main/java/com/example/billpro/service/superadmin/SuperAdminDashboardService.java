package com.example.billpro.service.superadmin;

/**
 * Service pour gérer le tableau de bord du SuperAdmin.
 * Fournit les statistiques globales du système.
 */
public interface SuperAdminDashboardService {

    /**
     * Récupère toutes les statistiques pour le dashboard du SuperAdmin.
     * Inclut: nombre de managers, employés, clients, produits, factures et le chiffre d'affaires total.
     *
     * @return SuperAdminDashboardDto contenant toutes les statistiques
     */
    SuperAdminDashboardDto getDashboardStats();
}