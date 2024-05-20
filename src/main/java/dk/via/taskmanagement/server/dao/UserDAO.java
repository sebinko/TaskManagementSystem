package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;

import java.sql.SQLException;

public interface UserDAO {

    User getByUsername(String username) throws SQLException;


    User create(User user) throws SQLException;
//    User update(int id, String username, String password, String role, int workspaceId);


}
