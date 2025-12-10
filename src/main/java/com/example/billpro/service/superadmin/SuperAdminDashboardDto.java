package com.example.billpro.service.superadmin;

public class SuperAdminDashboardDto {

    private long totalManagers;
    private long totalEmployes;
    private long totalClients;
    private long totalProduits;
    private long totalFactures;
    private double chiffreAffairesTotal;

    // Getters
    public long getTotalManagers() { return totalManagers; }
    public long getTotalEmployes() { return totalEmployes; }
    public long getTotalClients() { return totalClients; }
    public long getTotalProduits() { return totalProduits; }
    public long getTotalFactures() { return totalFactures; }
    public double getChiffreAffairesTotal() { return chiffreAffairesTotal; }

    // Setters
    public void setTotalManagers(long totalManagers) { this.totalManagers = totalManagers; }
    public void setTotalEmployes(long totalEmployes) { this.totalEmployes = totalEmployes; }
    public void setTotalClients(long totalClients) { this.totalClients = totalClients; }
    public void setTotalProduits(long totalProduits) { this.totalProduits = totalProduits; }
    public void setTotalFactures(long totalFactures) { this.totalFactures = totalFactures; }
    public void setChiffreAffairesTotal(double chiffreAffairesTotal) { this.chiffreAffairesTotal = chiffreAffairesTotal; }
}
