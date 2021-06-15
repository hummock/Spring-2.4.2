package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.DAO.UserDao;
import web.model.Role;
import web.model.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createNewUser(String name, String lastname, int age, String login, String password, String role) {
        userDao.createNewUser(name, lastname, age, login, password, role);
    }

    @Override
    public void editUser(Long id, User user) {
        userDao.editUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public Role getRoleByName(String name) {
        return userDao.getRoleByName(name);
    }

}
