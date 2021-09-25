package design.abdelhak.kahrakib.networks.responses;

import java.util.List;

public class RoleResponseModel {

    private Long roleId;
    private String role;
    private List<UtilisateurResponseModel> utilisateurs;

    public RoleResponseModel(Long roleId, String role, List<UtilisateurResponseModel> utilisateurs) {
        this.roleId = roleId;
        this.role = role;
        this.utilisateurs = utilisateurs;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UtilisateurResponseModel> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<UtilisateurResponseModel> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
