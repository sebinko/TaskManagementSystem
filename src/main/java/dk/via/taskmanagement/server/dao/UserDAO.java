package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {

    User getByUsername(String username) throws SQLException;

    User create(User user) throws SQLException;

    ArrayList<User> getUsersWithoutWorkspace() throws SQLException;


}
