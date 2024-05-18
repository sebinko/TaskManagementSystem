package dk.via.taskmanagement.client;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.shared.Connector;

import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImplementation extends UnicastRemoteObject implements Client {
    private final Connector connector;
    private PropertyChangeSupport propertyChangeSupport;

    public ClientImplementation(Connector connector) throws RemoteException {
        this.connector = connector;
        propertyChangeSupport = new PropertyChangeSupport(this);
//        this.connector.addRemotePropertyChangeListener(this);
        //TODO hore
    }

    @Override
    public Workspace createWorkspace(Workspace workspace) throws RemoteException {
        return connector.createWorkspace(workspace);
    }

    @Override
    public Workspace getWorkspace(User requestingUser) throws RemoteException {
        return connector.getWorkspace(requestingUser);
    }

    @Override
    public void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException {
        connector.addWorkSpaceUser(workspace, newUser);
    }

    @Override
    public User createUser(User user) throws RemoteException {
        connector.createUser(user);

        return user;
    }
}
