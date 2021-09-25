package design.abdelhak.kahrakib.networks.requests;

public class ChantierRequestModel {

    private String nom;
    private String adresse;
    private String directionImputation;

    public ChantierRequestModel() {
    }

    public ChantierRequestModel(String nom, String adresse, String directionImputation) {
        this.nom = nom;
        this.adresse = adresse;
        this.directionImputation = directionImputation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDirectionImputation() {
        return directionImputation;
    }

    public void setDirectionImputation(String directionImputation) {
        this.directionImputation = directionImputation;
    }
}
