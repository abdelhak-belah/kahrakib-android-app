package design.abdelhak.kahrakib.networks.responses;

import java.io.Serializable;
import java.util.Date;

public class UtilisateurResponseModel implements Serializable {

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

    public UtilisateurResponseModel(Long utilisateurId, String email, String motDePasse, String nom, String prenom, String dateNaissance, int age, String telephone, RoleResponseModel role, String dateCreation) {
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

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

}
