package design.abdelhak.kahrakib.networks.requests;

public class EtatCassierRequestModel {

    private Boolean etatCassier = false;

    public EtatCassierRequestModel() {
    }

    public EtatCassierRequestModel(Boolean etatCassier) {
        this.etatCassier = etatCassier;
    }

    public Boolean getEtatCassier() {
        return etatCassier;
    }

    public void setEtatCassier(Boolean etatCassier) {
        this.etatCassier = etatCassier;
    }
}
