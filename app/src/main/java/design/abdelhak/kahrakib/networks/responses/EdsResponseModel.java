package design.abdelhak.kahrakib.networks.responses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import design.abdelhak.kahrakib.networks.requests.EtatRequestModel;

public class EdsResponseModel implements Serializable {

    private Long edsId;
    private String imputation;
    private BigDecimal montantGlobal;
    private String dateCreation;
    private ComptableResponseModel comptable;
    private CassierResponseModel cassier;
    private CassierRespoResponseModel cassierRespo;
    private List<DpsResponseModel> dpss;
    private EtatRequestModel etat;

    public EdsResponseModel(Long edsId, String imputation, BigDecimal montantGlobal, String dateCreation, ComptableResponseModel comptable, CassierResponseModel cassier, CassierRespoResponseModel cassierRespo, List<DpsResponseModel> dpss, EtatRequestModel etat) {
        this.edsId = edsId;
        this.imputation = imputation;
        this.montantGlobal = montantGlobal;
        this.dateCreation = dateCreation;
        this.comptable = comptable;
        this.cassier = cassier;
        this.cassierRespo = cassierRespo;
        this.dpss = dpss;
        this.etat = etat;
    }

    public Long getEdsId() {
        return edsId;
    }

    public void setEdsId(Long edsId) {
        this.edsId = edsId;
    }

    public String getImputation() {
        return imputation;
    }

    public void setImputation(String imputation) {
        this.imputation = imputation;
    }

    public BigDecimal getMontantGlobal() {
        return montantGlobal;
    }

    public void setMontantGlobal(BigDecimal montantGlobal) {
        this.montantGlobal = montantGlobal;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ComptableResponseModel getComptable() {
        return comptable;
    }

    public void setComptable(ComptableResponseModel comptable) {
        this.comptable = comptable;
    }

    public CassierResponseModel getCassier() {
        return cassier;
    }

    public void setCassier(CassierResponseModel cassier) {
        this.cassier = cassier;
    }

    public CassierRespoResponseModel getCassierRespo() {
        return cassierRespo;
    }

    public void setCassierRespo(CassierRespoResponseModel cassierRespo) {
        this.cassierRespo = cassierRespo;
    }

    public List<DpsResponseModel> getDpss() {
        return dpss;
    }

    public void setDpss(List<DpsResponseModel> dpss) {
        this.dpss = dpss;
    }

    public EtatRequestModel getEtat() {
        return etat;
    }

    public void setEtat(EtatRequestModel etat) {
        this.etat = etat;
    }
}
