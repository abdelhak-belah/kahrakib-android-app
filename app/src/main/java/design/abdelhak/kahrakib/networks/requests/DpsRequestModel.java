package design.abdelhak.kahrakib.networks.requests;

import java.util.List;

public class DpsRequestModel {

    private String prestataire;
    private String adressePrestataire;
    private Long clientId;
    private List<AchatWithDpsRequestModel> achats;

    public DpsRequestModel() {
    }

    public DpsRequestModel(String prestataire, String adressePrestataire, Long clientId, List<AchatWithDpsRequestModel> achats) {
        this.prestataire = prestataire;
        this.adressePrestataire = adressePrestataire;
        this.clientId = clientId;
        this.achats = achats;
    }

    public String getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(String prestataire) {
        this.prestataire = prestataire;
    }

    public String getAdressePrestataire() {
        return adressePrestataire;
    }

    public void setAdressePrestataire(String adressePrestataire) {
        this.adressePrestataire = adressePrestataire;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<AchatWithDpsRequestModel> getAchats() {
        return achats;
    }

    public void addAchats(AchatWithDpsRequestModel achatWithDpsReq) {
        this.achats.add(achatWithDpsReq);
    }
}
