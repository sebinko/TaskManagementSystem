package dk.via.taskmanagement.server;

import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.model.WorkspaceUserList;
import dk.via.taskmanagement.server.dao.UserDAOImplementation;
import dk.via.taskmanagement.shared.Connector;

import java.rmi.RemoteException;
import java.sql.SQLException;

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
        try {
            UserDAOImplementation.getInstance().create(user);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }

        return user;
    }

    @Override
    public User getUserByUsername(String username) throws RemoteException {
        try {
            return UserDAOImplementation.getInstance().getByUsername(username);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public User authenticateUser(String username, String password) throws RemoteException, AuthenticationException {
        try {
            User user = UserDAOImplementation.getInstance().getByUsername(username);

            if (user == null || !user.getPassword().equals(password)) {
                throw new AuthenticationException("Invalid username or password");
            }

            return user;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }
}
