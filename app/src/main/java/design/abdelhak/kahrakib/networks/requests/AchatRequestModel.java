package design.abdelhak.kahrakib.networks.requests;

import java.math.BigDecimal;

public class AchatRequestModel {

    private String designation;
    private int quantite;
    private BigDecimal prixUnitaire;
    private Long dpsId;

    public AchatRequestModel() {
    }

    public AchatRequestModel(String designation, int quantite, BigDecimal prixUnitaire, Long dpsId) {
        this.designation = designation;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.dpsId = dpsId;
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

    public Long getDpsId() {
        return dpsId;
    }

    public void setDpsId(Long dpsId) {
        this.dpsId = dpsId;
    }
}
