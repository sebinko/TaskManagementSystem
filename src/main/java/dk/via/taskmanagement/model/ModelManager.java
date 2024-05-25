package dk.via.taskmanagement.model;

import dk.via.taskmanagement.client.Client;
import dk.via.taskmanagement.client.ClientImplementation;
import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.shared.Connector;
import dk.via.taskmanagement.validation.TaskValidation;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModelManager implements Model, PropertyChangeListener {
    private final Client client;
    private final PropertyChangeSupport support;

    public ModelManager(Connector connector) throws RemoteException {
        this.client = new ClientImplementation(connector);
        this.client.addPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public synchronized Workspace createWorkspace(Workspace workspace) {
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
    public synchronized void addWorkSpaceUser(Workspace workspace, User newUser) {
        try {
            client.addWorkSpaceUser(workspace, newUser);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized User createUser(User user) {
        try {
            return client.createUser(user);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return client.getUserByUsername(username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized User authenticateUser(String username, String password) throws AuthenticationException {
        try {
            return client.authenticateUser(username, password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<User> getUsersWithoutWorkspace() {
        try {
            return client.getUsersWithoutWorkspace();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<User> getUsersForWorkspace(Workspace workspace) {
        try {
            return client.getUsersForWorkspace(workspace);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized Task createTask(Task task) throws ValidationException {
        try {
            TaskValidation.validate(task);

            return client.createTask(task);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized Task updateTask(Task task) throws ValidationException {
        try {
            TaskValidation.validate(task);

            return client.updateTask(task);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized Task deleteTask(Task task) {
        try {
            return client.deleteTask(task);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized Task startTask(Task task) {
        try {
            task.startTask();

            return client.startTask(task);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized Task completeTask(Task task) {
        try {
            task.completeTask();

            return client.completeTask(task);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Task> getTasksForWorkspace(Workspace workspace) {
        try {
            return client.getTasksForWorkspace(workspace);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if (evt.getPropertyName().equals("createWorkspace")) {
                support.firePropertyChange(new PropertyChangeEvent(this, "createWorkspace", null, evt.getNewValue()));
            } else if (evt.getPropertyName().equals("addWorkSpaceUser")) {
                support.firePropertyChange(new PropertyChangeEvent(this, "addWorkSpaceUser", null, evt.getNewValue()));
            } else if (evt.getPropertyName().equals("updateTasks")) {
                support.firePropertyChange(new PropertyChangeEvent(this, "updateTasks", null, evt.getNewValue()));
            }
        });
    }
}
