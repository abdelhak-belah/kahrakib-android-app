package design.abdelhak.kahrakib.networks.responses;

import java.io.Serializable;
import java.util.List;

public class ChantierResponseModel implements Serializable {

    private Long chantierId;
    private String nom;
    private String imputation;
    private String adresse;
    private DirectionResponseModel direction;
    private List<ClientResponseModel> clients;
    private CassierResponseModel cassier;
    private CassierRespoResponseModel cassierRespo;

    public ChantierResponseModel(Long chantierId, String nom, String imputation, String adresse, DirectionResponseModel direction, List<ClientResponseModel> clients, CassierResponseModel cassier, CassierRespoResponseModel cassierRespo) {
        this.chantierId = chantierId;
        this.nom = nom;
        this.imputation = imputation;
        this.adresse = adresse;
        this.direction = direction;
        this.clients = clients;
        this.cassier = cassier;
        this.cassierRespo = cassierRespo;
    }

    public Long getChantierId() {
        return chantierId;
    }

    public void setChantierId(Long chantierId) {
        this.chantierId = chantierId;
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

    public DirectionResponseModel getDirection() {
        return direction;
    }

    public void setDirection(DirectionResponseModel direction) {
        this.direction = direction;
    }

    public List<ClientResponseModel> getClients() {
        return clients;
    }

    public void setClients(List<ClientResponseModel> clients) {
        this.clients = clients;
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
}
