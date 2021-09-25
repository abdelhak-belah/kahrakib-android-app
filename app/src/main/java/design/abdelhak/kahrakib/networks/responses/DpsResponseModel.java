package design.abdelhak.kahrakib.networks.responses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DpsResponseModel implements Serializable {

    private Long dpsId;
    private String imputation;
    private Boolean etatClient;
    private Boolean etatCassier;
    private String prestataire;
    private String adressePrestataire;
    private BigDecimal totalAchatMontant;
    private String dateDeCreation;
    private ClientResponseModel client;
    private CassierResponseModel cassier;
    private List<AchatResponseModel> achats;
    private EdsResponseModel eds;

    public DpsResponseModel(Long dpsId, String imputation, Boolean etatClient, Boolean etatCassier, String prestataire, String adressePrestataire, BigDecimal totalAchatMontant, String dateDeCreation, ClientResponseModel client, CassierResponseModel cassier, List<AchatResponseModel> achats, EdsResponseModel eds) {
        this.dpsId = dpsId;
        this.imputation = imputation;
        this.etatClient = etatClient;
        this.etatCassier = etatCassier;
        this.prestataire = prestataire;
        this.adressePrestataire = adressePrestataire;
        this.totalAchatMontant = totalAchatMontant;
        this.dateDeCreation = dateDeCreation;
        this.client = client;
        this.cassier = cassier;
        this.achats = achats;
        this.eds = eds;
    }

    public DpsResponseModel() {

    }

    public Long getDpsId() {
        return dpsId;
    }

    public void setDpsId(Long dpsId) {
        this.dpsId = dpsId;
    }

    public String getImputation() {
        return imputation;
    }

    public void setImputation(String imputation) {
        this.imputation = imputation;
    }

    public Boolean getEtatClient() {
        return etatClient;
    }

    public void setEtatClient(Boolean etatClient) {
        this.etatClient = etatClient;
    }

    public Boolean getEtatCassier() {
        return etatCassier;
    }

    public void setEtatCassier(Boolean etatCassier) {
        this.etatCassier = etatCassier;
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

    public String getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(String dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public ClientResponseModel getClient() {
        return client;
    }

    public void setClient(ClientResponseModel client) {
        this.client = client;
    }

    public CassierResponseModel getCassier() {
        return cassier;
    }

    public void setCassier(CassierResponseModel cassier) {
        this.cassier = cassier;
    }

    public List<AchatResponseModel> getAchats() {
        return achats;
    }

    public void setAchats(List<AchatResponseModel> achats) {
        this.achats = achats;
    }

    public EdsResponseModel getEds() {
        return eds;
    }

    public void setEds(EdsResponseModel eds) {
        this.eds = eds;
    }

    public BigDecimal getTotalAchatMontant() {
        return totalAchatMontant;
    }

    public void setTotalAchatMontant(BigDecimal totalAchatMontant) {
        this.totalAchatMontant = totalAchatMontant;
    }
}
