package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import org.postgresql.Driver;

import java.sql.*;

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
    public User getById(int id) throws SQLException {
//        try (Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
//            statement.setInt(1, id);
//        }

        return null;
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // TODO workspace
                Workspace workspace = WorkspaceDAOImplementation.getInstance().getById(resultSet.getInt(5));
                return new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        workspace
                );
            } else {
                return null;
            }

        }
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Workspace workspace = WorkspaceDAOImplementation.getInstance().getById(resultSet.getInt(5));

                return new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        workspace
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

        // select the user back
        user = getByUsername(user.getUserName());

        return user;
    }


}
