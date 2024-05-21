package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import org.postgresql.Driver;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOImplementation implements UserDAO {
    private static UserDAOImplementation instance;

    private UserDAOImplementation() throws SQLException {
        DriverManager.registerDriver(new Driver());
    }

    public static UserDAOImplementation getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserDAOImplementation();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=task_management_system", "postgres", "");
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Workspace workspace = WorkspaceDAOImplementation.getInstance().getById(resultSet.getInt(5));
                return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), workspace);
            } else {
                return null;
            }

        }
    }

    @Override
    public User create(User user) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, password, role) VALUES (?, ?, ?)");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
        }

        user = getByUsername(user.getUserName());

        return user;
    }

    @Override
    public ArrayList<User> getUsersWithoutWorkspace() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE workspace_id IS NULL");
            ResultSet resultSet = statement.executeQuery();

            ArrayList<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), null));
            }

            return users;
        }
    }

    public ArrayList<User> getUsersForWorkspace(Workspace workspace) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE workspace_id = ?");
            statement.setInt(1, workspace.getId());
            ResultSet resultSet = statement.executeQuery();

            ArrayList<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), workspace));
            }

            return users;
        }
    }
}
