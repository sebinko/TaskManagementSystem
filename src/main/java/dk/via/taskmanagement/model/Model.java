package dk.via.taskmanagement.model;

import dk.via.taskmanagement.exceptions.AuthenticationException;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface Model {
    Workspace createWorkspace(Workspace workspace);

    Workspace getWorkspace(User requestingUser);

    void addWorkSpaceUser(Workspace workspace, User newUser);

    User createUser(User user);

    User getUserByUsername(String username);

    User authenticateUser(String username, String password) throws AuthenticationException;

    ArrayList<User> getUsersWithoutWorkspace();

    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
}
