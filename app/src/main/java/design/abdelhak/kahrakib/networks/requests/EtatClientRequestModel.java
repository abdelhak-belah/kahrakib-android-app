package design.abdelhak.kahrakib.networks.requests;

public class EtatClientRequestModel {

    private Boolean etatClient = false;

    public EtatClientRequestModel() {
    }

    public EtatClientRequestModel(Boolean etatClient) {
        this.etatClient = etatClient;
    }

    public Boolean getEtatClient() {
        return etatClient;
    }

    public void setEtatClient(Boolean etatClient) {
        this.etatClient = etatClient;
    }
}
