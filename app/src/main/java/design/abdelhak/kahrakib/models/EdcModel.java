package design.abdelhak.kahrakib.models;

import java.util.List;

public class EdcModel {

    private int numeroEtat;
    private double montentGlobal;
    private List<DpsModel> dpsListe;
    private String imputation;
    private boolean etat;

    public EdcModel(int numeroEtat, double montentGlobal, List<DpsModel> dpsListe, String imputation, boolean etat) {
        this.numeroEtat = numeroEtat;
        this.montentGlobal = montentGlobal;
        this.dpsListe = dpsListe;
        this.imputation = imputation;
        this.etat = etat;
    }

    public int getNumeroEtat() {
        return numeroEtat;
    }

    public void setNumeroEtat(int numeroEtat) {
        this.numeroEtat = numeroEtat;
    }

    public double getMontentGlobal() {
        return montentGlobal;
    }

    public void setMontentGlobal(double montentGlobal) {
        this.montentGlobal = montentGlobal;
    }

    public List<DpsModel> getDpsListe() {
        return dpsListe;
    }

    public void setDpsListe(List<DpsModel> dpsListe) {
        this.dpsListe = dpsListe;
    }

    public String getImputation() {
        return imputation;
    }

    public void setImputation(String imputation) {
        this.imputation = imputation;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}
