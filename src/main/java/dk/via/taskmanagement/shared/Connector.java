package dk.via.taskmanagement.shared;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Connector extends Remote {
    Workspace createWorkspace(Workspace workspace) throws RemoteException;
    Workspace getWorkspace(User requestingUser) throws RemoteException;
    void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException;
//    void removeWorkSpaceUser(User user) throws RemoteException;



}
