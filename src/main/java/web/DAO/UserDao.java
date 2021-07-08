package web.DAO;

import web.model.Role;
import web.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    void createNewUser(User user);
    void editUser(User user);
    void deleteUserById(Long id);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    Optional<User> getUserByLogin(String login);
    Optional<Role> getRoleByName(String name);
}
