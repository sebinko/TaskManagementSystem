package dk.via.taskmanagement.client;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.rmi.RemoteException;

public interface Client {
    Workspace createWorkspace(Workspace workspace) throws RemoteException;
    Workspace getWorkspace(User requestingUser) throws RemoteException;
    void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException;

    User createUser(User user) throws RemoteException;
}
