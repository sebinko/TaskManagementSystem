package dk.via.taskmanagement.shared;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Connector extends Remote {
    Workspace createWorkspace(Workspace workspace) throws RemoteException;

    Workspace getWorkspace(User requestingUser) throws RemoteException;

    void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException;
//    void removeWorkSpaceUser(User user) throws RemoteException;

    User createUser(User user) throws RemoteException;

    User getUserByUsername(String username) throws RemoteException;

    User authenticateUser(String username, String password) throws RemoteException, AuthenticationException;

    ArrayList<User> getUsersWithoutWorkspace() throws RemoteException;

    ArrayList<User> getUsersForWorkspace(Workspace workspace) throws RemoteException;
    
    /*
    TASKS
     */


    Task createTask(Task task) throws RemoteException;

    Task updateTask(Task task) throws RemoteException;

    Task deleteTask(Task task) throws RemoteException;

    Task startTask(Task task) throws RemoteException;

    Task completeTask(Task task) throws RemoteException;

    ArrayList<Task> getTasksForWorkspace(Workspace workspace) throws RemoteException;

    /*
    PROPERTY CHANGE LISTENER
     */

    void addRemotePropertyChangeListener(RemotePropertyChangeListener listener) throws RemoteException;
}
