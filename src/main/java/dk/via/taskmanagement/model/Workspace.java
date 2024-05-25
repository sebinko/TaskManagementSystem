package dk.via.taskmanagement.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Workspace implements Serializable {
    private Integer id;
    private String name;

    private ArrayList<User> users = new ArrayList<>();

    public Workspace(String name) {
        this.id = null;
        this.name = name;
    }

    public Workspace(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
