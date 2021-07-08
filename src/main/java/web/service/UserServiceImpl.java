package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.DAO.UserDao;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void UserServiceImpl(UserDao userDao) {
        this.userDao=userDao;
    }

    @Autowired
    public void UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    @Transactional
    public void createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPasswordReal()));
        user.setRoles(new HashSet<>());

        if (user.getRoles().equals("ROLE_ADMIN")) {

            user.getRoles().add(userDao.getRoleByName("ROLE_ADMIN").get());
            user.getRoles().add(userDao.getRoleByName("ROLE_USER").get());
        } else {
            user.getRoles().add( userDao.getRoleByName("ROLE_USER").get());
        }
        userDao.createNewUser(user);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        user.setPasswordReal(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPasswordReal()));
//        user.setRoles(getUserByLogin(user.getLogin()).getRoles());
        userDao.editUser(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }

    @Override
    @Transactional
    public Optional<User> getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

}