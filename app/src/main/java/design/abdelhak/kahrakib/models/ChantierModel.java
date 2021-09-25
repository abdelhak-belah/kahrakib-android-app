package design.abdelhak.kahrakib.models;

public class ChantierModel {

    private String nom;
    private String imputation;
    private String adresse;
    private String direction;


    public ChantierModel(String nom, String imputation, String adresse, String direction) {
        this.nom = nom;
        this.imputation = imputation;
        this.adresse = adresse;
        this.direction = direction;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImputation() {
        return imputation;
    }

    public void setImputation(String imputation) {
        this.imputation = imputation;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
