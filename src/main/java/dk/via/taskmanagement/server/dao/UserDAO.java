package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;

import java.sql.SQLException;

public interface UserDAO {
    User getById(int id) throws SQLException;

    User getByUsername(String username) throws SQLException;

    User getByUsernameAndPassword(String username, String password);

    User create(User user) throws SQLException;
//    User update(int id, String username, String password, String role, int workspaceId);


}
