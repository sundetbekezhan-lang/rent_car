package model;

public class SystemUser {

    private String username;
    private Role role;

    public SystemUser(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}
