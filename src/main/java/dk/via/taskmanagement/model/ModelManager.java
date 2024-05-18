package dk.via.taskmanagement.model;

import dk.via.taskmanagement.client.Client;
import dk.via.taskmanagement.client.ClientImplementation;
import dk.via.taskmanagement.shared.Connector;

import java.rmi.RemoteException;

public class ModelManager implements Model{
    private final Client client;

    public ModelManager(Connector connector) throws RemoteException{
        this.client = new ClientImplementation(connector);
    }

    @Override
    public Workspace createWorkspace(Workspace workspace) {
        try {
            return client.createWorkspace(workspace);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Workspace getWorkspace(User requestingUser) {
        try {
            return client.getWorkspace(requestingUser);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addWorkSpaceUser(Workspace workspace, User newUser) {
        try {
            client.addWorkSpaceUser(workspace, newUser);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User createUser(User user) {
        try {
            return client.createUser(user);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
