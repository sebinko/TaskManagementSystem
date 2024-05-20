package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import org.postgresql.Driver;

import java.sql.*;

public class WorkspaceDAOImplementation implements WorkspaceDAO {
    private static WorkspaceDAOImplementation instance;

    private WorkspaceDAOImplementation() throws SQLException {
        DriverManager.registerDriver(new Driver());
    }

    public static WorkspaceDAOImplementation getInstance() throws SQLException {
        if (instance == null) {
            instance = new WorkspaceDAOImplementation();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=task_management_system", "postgres", "");
    }

    @Override
    public Workspace createWorkspace(Workspace workspace) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO workspaces (name) VALUES (?)");
            statement.setString(1, workspace.getName());
            statement.executeUpdate();
        }

        workspace = getByName(workspace.getName());

        return workspace;

    }

    public Workspace getByName(String name) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM workspaces WHERE name = ?");
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Workspace(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
            } else {
                return null;
            }

        }
    }

    @Override
    public Workspace getById(int id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM workspaces WHERE workspace_id = ?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Workspace(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
            } else {
                return null;
            }

        }
    }

    @Override
    public void addWorkSpaceUser(Workspace workspace, User newUser) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET workspace_id = ? WHERE user_id = ?");
            statement.setInt(1, workspace.getId());
            statement.setInt(2, newUser.getId());

            statement.executeUpdate();
        }
    }
}
