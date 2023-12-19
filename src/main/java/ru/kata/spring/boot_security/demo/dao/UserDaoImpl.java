package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        System.out.println("delete user by id: " + id);
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User getUserByUserName(String username) {
        return entityManager.createQuery(
                        "SELECT user FROM User user join fetch  user.roles WHERE user.username =:username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}