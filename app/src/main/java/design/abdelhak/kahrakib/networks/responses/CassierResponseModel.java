package design.abdelhak.kahrakib.networks.responses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CassierResponseModel {

    private Long utilisateurId;
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private int age;
    private String telephone;
    private RoleResponseModel role;
    private String dateCreation;
    private BigDecimal budget;
    private ChantierResponseModel chantier;
    private List<DpsResponseModel> dpss = new ArrayList<>();
    private List<EdsResponseModel> edss = new ArrayList<>();

    public CassierResponseModel(Long utilisateurId, String email, String motDePasse, String nom, String prenom, String dateNaissance, int age, String telephone, RoleResponseModel role, String dateCreation, BigDecimal budget, ChantierResponseModel chantier, List<DpsResponseModel> dpss, List<EdsResponseModel> edss) {
        this.utilisateurId = utilisateurId;
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.age = age;
        this.telephone = telephone;
        this.role = role;
        this.dateCreation = dateCreation;
        this.budget = budget;
        this.chantier = chantier;
        this.dpss = dpss;
        this.edss = edss;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public RoleResponseModel getRole() {
        return role;
    }

    public void setRole(RoleResponseModel role) {
        this.role = role;
    }



    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public ChantierResponseModel getChantier() {
        return chantier;
    }

    public void setChantier(ChantierResponseModel chantier) {
        this.chantier = chantier;
    }

    public List<DpsResponseModel> getDpss() {
        return dpss;
    }

    public void setDpss(List<DpsResponseModel> dpss) {
        this.dpss = dpss;
    }

    public List<EdsResponseModel> getEdss() {
        return edss;
    }

    public void setEdss(List<EdsResponseModel> edss) {
        this.edss = edss;
    }
}
