package dk.via.taskmanagement.model;

import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.exceptions.ValidationException;

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

    ArrayList<User> getUsersForWorkspace(Workspace workspace);

    Task createTask(Task task) throws ValidationException;

    Task updateTask(Task task) throws ValidationException;

    Task deleteTask(Task task);

    Task startTask(Task task);

    Task completeTask(Task task);

    ArrayList<Task> getTasksForWorkspace(Workspace workspace);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
