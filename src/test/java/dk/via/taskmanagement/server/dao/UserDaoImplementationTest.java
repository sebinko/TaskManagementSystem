package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImplementationTest {
    @Test
    void testGetUsersWithoutWorkspace() {
        try {
            ArrayList<User> users = UserDAOImplementation.getInstance().getUsersWithoutWorkspace();

            System.out.println(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
