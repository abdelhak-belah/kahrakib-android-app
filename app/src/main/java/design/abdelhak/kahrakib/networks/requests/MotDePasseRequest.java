package design.abdelhak.kahrakib.networks.requests;

public class MotDePasseRequest {

    private Long utilisateurId;
    private String email;
    private String currentMotDePasse;
    private String nouveauMotDePasse;

    public MotDePasseRequest() {
    }

    public MotDePasseRequest(Long utilisateurId, String email, String currentMotDePasse, String nouveauMotDePasse) {
        this.utilisateurId = utilisateurId;
        this.email = email;
        this.currentMotDePasse = currentMotDePasse;
        this.nouveauMotDePasse = nouveauMotDePasse;
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

    public String getCurrentMotDePasse() {
        return currentMotDePasse;
    }

    public void setCurrentMotDePasse(String currentMotDePasse) {
        this.currentMotDePasse = currentMotDePasse;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
}
