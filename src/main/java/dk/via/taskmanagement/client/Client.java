package dk.via.taskmanagement.client;

import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client {
    Workspace createWorkspace(Workspace workspace) throws RemoteException;

    Workspace getWorkspace(User requestingUser) throws RemoteException;

    void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException;

    User createUser(User user) throws RemoteException;

    User getUserByUsername(String username) throws RemoteException;

    User authenticateUser(String username, String password) throws RemoteException, AuthenticationException;

    ArrayList<User> getUsersWithoutWorkspace() throws RemoteException;
    ArrayList<User> getUsersForWorkspace(Workspace workspace) throws RemoteException;

    void addPropertyChangeListener(PropertyChangeListener listener);
}
