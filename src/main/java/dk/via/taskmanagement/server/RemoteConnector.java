package dk.via.taskmanagement.server;

import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.server.dao.UserDAOImplementation;
import dk.via.taskmanagement.server.dao.WorkspaceDAOImplementation;
import dk.via.taskmanagement.shared.Connector;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class RemoteConnector implements Connector {

    @Override
    public Workspace createWorkspace(Workspace workspace) throws RemoteException {
        try {
            return WorkspaceDAOImplementation.getInstance().createWorkspace(workspace);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public Workspace getWorkspace(User requestingUser) throws RemoteException {
        return null;
    }

    @Override
    public void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException {
        try {
            WorkspaceDAOImplementation.getInstance().addWorkSpaceUser(workspace, newUser);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public User createUser(User user) throws RemoteException {
        try {
            return UserDAOImplementation.getInstance().create(user);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
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
