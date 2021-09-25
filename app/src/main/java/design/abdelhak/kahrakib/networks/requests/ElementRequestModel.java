package design.abdelhak.kahrakib.networks.requests;

public class ElementRequestModel {

    private String nom;

    public ElementRequestModel() {
    }

    public ElementRequestModel(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
