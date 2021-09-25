package design.abdelhak.kahrakib.models;

import java.util.List;

public class DpsModel {

    private String dateAchat;
    private String nomPrestataire;
    private String adressePrestataire;
    private String imputation;
    private UserModel client;
    private List<AchatModel> achatList;
    private Double prixTotal;

    public DpsModel(String dateAchat, String nomPrestataire, String adressePrestataire, String imputation, UserModel client, List<AchatModel> achatList, Double prixTotal) {
        this.dateAchat = dateAchat;
        this.nomPrestataire = nomPrestataire;
        this.adressePrestataire = adressePrestataire;
        this.imputation = imputation;
        this.client = client;
        this.achatList = achatList;
        this.prixTotal = prixTotal;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getNomPrestataire() {
        return nomPrestataire;
    }

    public void setNomPrestataire(String nomPrestataire) {
        this.nomPrestataire = nomPrestataire;
    }

    public String getAdressePrestataire() {
        return adressePrestataire;
    }

    public void setAdressePrestataire(String adressePrestataire) {
        this.adressePrestataire = adressePrestataire;
    }

    public String getImputation() {
        return imputation;
    }

    public void setImputation(String imputation) {
        this.imputation = imputation;
    }

    public UserModel getClient() {
        return client;
    }

    public void setClient(UserModel client) {
        this.client = client;
    }

    public List<AchatModel> getAchatList() {
        return achatList;
    }

    public void setAchatList(List<AchatModel> achatList) {
        this.achatList = achatList;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }
}
