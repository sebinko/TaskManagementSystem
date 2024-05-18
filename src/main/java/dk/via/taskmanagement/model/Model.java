package dk.via.taskmanagement.model;

import java.rmi.RemoteException;

public interface Model {
    Workspace createWorkspace(Workspace workspace);
    Workspace getWorkspace(User requestingUser);
    void addWorkSpaceUser(Workspace workspace, User newUser);

    User createUser(User user);
}
