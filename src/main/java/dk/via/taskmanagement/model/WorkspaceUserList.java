package dk.via.taskmanagement.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Holds the list of the users in the workspace
 */
public class WorkspaceUserList implements Serializable {
    private ArrayList<User> users;

    public WorkspaceUserList() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
