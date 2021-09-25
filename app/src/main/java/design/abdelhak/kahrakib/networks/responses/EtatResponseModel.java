package design.abdelhak.kahrakib.networks.responses;

import java.util.List;

public class EtatResponseModel {

    private Long etatId;
    private String etat;
    private List<EdsResponseModel> edss;

    public EtatResponseModel(Long etatId, String etat, List<EdsResponseModel> edss) {
        this.etatId = etatId;
        this.etat = etat;
        this.edss = edss;
    }

    public Long getEtatId() {
        return etatId;
    }

    public void setEtatId(Long etatId) {
        this.etatId = etatId;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public List<EdsResponseModel> getEdss() {
        return edss;
    }

    public void setEdss(List<EdsResponseModel> edss) {
        this.edss = edss;
    }
}
