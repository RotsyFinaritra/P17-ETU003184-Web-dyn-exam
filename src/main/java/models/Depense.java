package main.java.models;

import java.util.List;

import main.java.utils.BaseObject;
import main.java.utils.GenericClassOO;

public class Depense extends GenericClassOO {

    int idPrevision;
    double montant;

    public Depense(int id) {
        super(id, "db_s2_ETU003184_depense");
    }

    public Depense(int id, int idPrevision, double montant) {
        super(id, "db_s2_ETU003184_depense");
        this.idPrevision = idPrevision;
        this.montant = montant;
    }

    public int getIdPrevision() {
        return idPrevision;
    }

    public void setIdPrevision(int idPrevision) {
        this.idPrevision = idPrevision;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    
    public double getDepenseTotalByIdPrevision(int idPrevision) throws Exception
    {
        Depense depenseInstance = new Depense(0);
        List<BaseObject> depenses = depenseInstance.findAll();

        double totalDepense = 0;

        for (BaseObject depense : depenses) {
            Depense castedDepense = ((Depense)depense);
            if (castedDepense.getIdPrevision() == idPrevision) {
                totalDepense += castedDepense.getMontant();
            }
        }
        return totalDepense;
    }
    
    
}
