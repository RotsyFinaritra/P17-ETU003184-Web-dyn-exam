package main.java.models;

import java.sql.Connection;
import main.java.utils.DB;

public class DepenseService {
    
    public static void ajouterDepense(int idPrevision, double montant) throws Exception
    {
        Prevision previsionInstance = new Prevision(0);
        try {
            Prevision prevision = (Prevision) previsionInstance.findById(idPrevision);
            if (prevision == null) {
                throw new Exception("Prevision inexistante");
            }
            else if (montant < 0) {
                throw new Exception("Le montant doit être positif");
            }
            else {
                Depense depenseInstance = new Depense(0);
                double totalDepense = depenseInstance.getDepenseTotalByIdPrevision(idPrevision);
                
                totalDepense += montant;

                double montantPrev = prevision.getMontant();
                double reste = montantPrev - totalDepense;
                if (reste < 0) {
                    throw new Exception("Solde insuffisant");
                } else {
                    Connection connection = DB.connect();
                    connection.setAutoCommit(false);
                    Depense depense = new Depense(0, idPrevision, montant);
                    try {
                        depense.save(connection);
                        connection.commit();
                    } catch (Exception e) {
                        connection.rollback();
                        throw new Exception("Erreur pendant l'insertion de la depense.");
                    } finally {
                        connection.close();
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static double getReste(int idPrevision) throws Exception
    {
        Prevision previsionInstance = new Prevision(0);
        Depense depenseInstance = new Depense(0);

        double reste = 0;
        try {
            Prevision prevision = (Prevision) previsionInstance.findById(idPrevision);
            double montantPrev = prevision.getMontant();
            double totalDepense = depenseInstance.getDepenseTotalByIdPrevision(idPrevision);
            reste = montantPrev - totalDepense;
        } catch (Exception e) {
            throw e;
        }

        return reste;
        
    }

}
