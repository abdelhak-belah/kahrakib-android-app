package design.abdelhak.kahrakib.networks.responses;

public class AuthResponseModel {

    private int id;
    private String jwt;
    private String email;
    private String role;

    public String getJwt() {
        return jwt;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
