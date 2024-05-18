package dk.via.taskmanagement.server;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.model.WorkspaceUserList;
import dk.via.taskmanagement.shared.Connector;

import java.rmi.RemoteException;

public class RemoteConnector implements Connector {
    private WorkspaceUserList workspaceUserList = new WorkspaceUserList();
    private Workspace workspace = new Workspace("Workspace 1", workspaceUserList);

    @Override
    public Workspace createWorkspace(Workspace workspace) throws RemoteException {
        this.workspace = workspace;

        return workspace;
    }

    @Override
    public Workspace getWorkspace(User requestingUser) throws RemoteException {
        return workspace;
    }

    @Override
    public void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException {
        workspace.getWorkspaceUserList().addUser(newUser);
    }

    @Override
    public User createUser(User user) throws RemoteException {
        return null;
    }
}
