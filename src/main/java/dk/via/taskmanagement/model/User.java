package dk.via.taskmanagement.model;

import java.io.Serializable;

public class User implements Serializable {
    private final Integer id;
    private String userName;
    private String password;
    private String role;
    private Workspace workspace;

    public User(String userName, String password, String role, Workspace workspace) {
        this.id = null;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.workspace = workspace;
    }

    public User(int id, String userName, String password, String role, Workspace workspace) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.workspace = workspace;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Workspace getWorkspace() {
        return workspace;
    }
}
