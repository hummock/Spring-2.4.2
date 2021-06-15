package web.DAO;

import web.model.Role;
import web.model.User;
import java.util.List;

public interface UserDao {

    void createNewUser(String name, String lastname, int age, String login,
                       String password, String role);
    void editUser(Long id, User user);
    void deleteUser(Long id);
    User getUser(Long id);
    List<User> getAllUsers();
    User getUserByLogin(String login);
    Role getRoleByName(String name);



}
