package dk.via.taskmanagement.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.shared.Connector;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientImplementation extends UnicastRemoteObject implements Client, RemotePropertyChangeListener {
    private final Connector connector;
    private PropertyChangeSupport propertyChangeSupport;

    public ClientImplementation(Connector connector) throws RemoteException {
        this.connector = connector;
        propertyChangeSupport = new PropertyChangeSupport(this);
        this.connector.addRemotePropertyChangeListener(this);
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
        return connector.createUser(user);
    }

    @Override
    public User getUserByUsername(String username) throws RemoteException {
        User user = connector.getUserByUsername(username);

        return user;
    }

    @Override
    public User authenticateUser(String username, String password) throws RemoteException, AuthenticationException {
        User user = connector.authenticateUser(username, password);

        return user;
    }

    @Override
    public ArrayList<User> getUsersWithoutWorkspace() throws RemoteException {
        return connector.getUsersWithoutWorkspace();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(RemotePropertyChangeEvent remotePropertyChangeEvent) throws RemoteException {
        Platform.runLater(() -> {
            if (remotePropertyChangeEvent.getPropertyName().equals("createWorkspace")) {
                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "createWorkspace", null, remotePropertyChangeEvent.getNewValue()));
            } else if (remotePropertyChangeEvent.getPropertyName().equals("addWorkSpaceUser")) {
                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "addWorkSpaceUser", null, remotePropertyChangeEvent.getNewValue()));
            }
        });

    }
}
