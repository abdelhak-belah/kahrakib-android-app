package design.abdelhak.kahrakib.networks.responses;

import java.io.Serializable;

public class ElementResponseModel implements Serializable {

    private Long elementId;
    private String nom;

    public ElementResponseModel(Long elementId, String nom) {
        this.elementId = elementId;
        this.nom = nom;
    }

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
