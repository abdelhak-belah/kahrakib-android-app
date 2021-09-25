package design.abdelhak.kahrakib.networks.requests;

public class EtatRequestModel {

    private String etat;

    public EtatRequestModel() {
    }

    public EtatRequestModel(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
