package web.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void createNewUser(String name, String lastname, int age, String login, String password, String role) {
        User user = new User(name, lastname, age, login, password);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(new HashSet<>());

        if (role.equals("ROLE_ADMIN")) {
            user.getRoles().add(getRoleByName("ROLE_ADMIN"));
            user.getRoles().add(getRoleByName("ROLE_USER"));
        } else {
            user.getRoles().add(getRoleByName("ROLE_USER"));
        }
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void editUser(Long id, User updateUser) {
        User userToBeUpdate = getUser(id);
        userToBeUpdate.setName(updateUser.getName());
        userToBeUpdate.setLastname(updateUser.getLastname());
        userToBeUpdate.setAge(updateUser.getAge());
        userToBeUpdate.setLogin(updateUser.getLogin());
        updateUser.setPasswordConfirm(updateUser.getPassword());
        updateUser.setPassword(passwordEncoder.encode(updateUser.getPasswordConfirm()));
        updateUser.setRoles(getUserByLogin(updateUser.getLogin()).getRoles());
        updateUser.setName(userToBeUpdate.getName());
        updateUser.setLastname(userToBeUpdate.getLastname());
        updateUser.setAge(userToBeUpdate.getAge());
        updateUser.setLogin(userToBeUpdate.getLogin());
        entityManager.merge(updateUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("From User").getResultList();
    }

    @Override
    public User getUserByLogin(String login) {
        return getAllUsers().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst().get();
    }

    @Override
    public Role getRoleByName(String name) {
        return (Role) entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name")
                .setParameter("name", name).getSingleResult();
    }


}
