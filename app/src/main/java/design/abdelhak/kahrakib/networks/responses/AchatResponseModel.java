package design.abdelhak.kahrakib.networks.responses;

import java.math.BigDecimal;

public class AchatResponseModel {

    private Long achatId;
    private String designation;
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal prixTotal;
    private DpsResponseModel dps;

    public AchatResponseModel(Long achatId, String designation, int quantite, BigDecimal prixUnitaire, BigDecimal prixTotal, DpsResponseModel dps) {
        this.achatId = achatId;
        this.designation = designation;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
        this.dps = dps;
    }

    public Long getAchatId() {
        return achatId;
    }

    public void setAchatId(Long achatId) {
        this.achatId = achatId;
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

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public DpsResponseModel getDps() {
        return dps;
    }

    public void setDps(DpsResponseModel dps) {
        this.dps = dps;
    }
}
