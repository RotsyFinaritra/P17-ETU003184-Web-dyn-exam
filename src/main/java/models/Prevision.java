package main.java.models;

import main.java.utils.GenericClassOO;

public class Prevision extends GenericClassOO {
    String libelle;
    double montant;
    
    public Prevision(int id) {
        super(id, "db_s2_ETU003184_prevision");
    }

    public Prevision(int id, String libelle, double montant) {
        super(id, "db_s2_ETU003184_prevision");
        this.libelle = libelle;
        this.montant = montant;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
