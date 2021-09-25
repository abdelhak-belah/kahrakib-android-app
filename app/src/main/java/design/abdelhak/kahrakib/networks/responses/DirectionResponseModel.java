package design.abdelhak.kahrakib.networks.responses;

import java.util.List;

public class DirectionResponseModel {

    private Long directionId;
    private String nom;
    private String imputation;
    private String adresse;
    private List<ChantierResponseModel> chantiers;
    private ComptableResponseModel comptable;

    public DirectionResponseModel(Long directionId, String nom, String imputation, String adresse, List<ChantierResponseModel> chantiers, ComptableResponseModel comptable) {
        this.directionId = directionId;
        this.nom = nom;
        this.imputation = imputation;
        this.adresse = adresse;
        this.chantiers = chantiers;
        this.comptable = comptable;
    }

    public Long getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Long directionId) {
        this.directionId = directionId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImputation() {
        return imputation;
    }

    public void setImputation(String imputation) {
        this.imputation = imputation;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<ChantierResponseModel> getChantiers() {
        return chantiers;
    }

    public void setChantiers(List<ChantierResponseModel> chantiers) {
        this.chantiers = chantiers;
    }

    public ComptableResponseModel getComptable() {
        return comptable;
    }

    public void setComptable(ComptableResponseModel comptable) {
        this.comptable = comptable;
    }
}
