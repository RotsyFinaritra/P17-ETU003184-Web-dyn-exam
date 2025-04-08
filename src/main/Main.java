package main;

import java.util.List;

import main.java.models.Depense;
import main.java.models.Prevision;
import main.java.utils.BaseObject;

public class Main {
    public static void main(String[] args) {
        Prevision p = new Prevision(0, "test", 10);
        Depense d = new Depense(0);
        try {
            // p.save();
            List<BaseObject> all = d.findAll();
            for (BaseObject baseObject : all) {
                System.out.println(baseObject);
            }
            // BaseObject pe = p.findById(1);
            // System.out.println(((Prevision) (pe)).getLibelle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
