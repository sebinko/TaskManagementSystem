package dk.via.taskmanagement.model;

import dk.via.taskmanagement.exceptions.AuthenticationException;

import java.rmi.RemoteException;
import java.sql.SQLException;

public interface Model {
    Workspace createWorkspace(Workspace workspace);
    Workspace getWorkspace(User requestingUser);
    void addWorkSpaceUser(Workspace workspace, User newUser);

    User createUser(User user);
    User getUserByUsername(String username);
    User authenticateUser(String username, String password) throws AuthenticationException;

}
