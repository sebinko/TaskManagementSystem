package dk.via.taskmanagement.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Workspace implements Serializable {
    private final int id;
    private String name;

    private ArrayList<User> users = new ArrayList<>();

    public Workspace(String name) {
        this.id = 0;
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
