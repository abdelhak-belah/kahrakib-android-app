package design.abdelhak.kahrakib.models;

import java.io.Serializable;

public class UserModel implements Serializable {

    private Long id;
    private String nom;
    private String prenom;
    private String direction;
    private String chantier;
    private String profile;
    private String email;
    private String naissance;
    private String telephone;
    private Boolean expanded;


    public UserModel(Long id,String nom, String prenom, String direction, String chantier, String profile, String email, String naissance, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.direction = direction;
        this.chantier = chantier;
        this.profile = profile;
        this.email = email;
        this.naissance = naissance;
        this.telephone = telephone;
        this.expanded = false;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNaissance() {
        return naissance;
    }

    public void setNaissance(String naissance) {
        this.naissance = naissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean isExpanded() {
        return expanded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
