package design.abdelhak.kahrakib.models;

public class AchatModel {

    private String designation;
    private Integer quantite;
    private Double prixUnitaire;

    public AchatModel(String designation, int quantite, Double prixUnitaire) {
        this.designation = designation;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
