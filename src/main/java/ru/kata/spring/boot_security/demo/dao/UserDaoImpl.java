package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getAllUsers() {
        //return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        TypedQuery<User> query = entityManager
                .createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUserById(int id) {
        entityManager.remove(getUserID(id).get());
    }

    @Override
    public void deleteUserByName(User user) {
        entityManager.remove(user);
    }

    @Override
    public Optional<User> getUserID(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> loadUserByUsername(String username) {
        TypedQuery<User> query = entityManager
                .createQuery("select user FROM User user JOIN fetch user.roles roles where user.name = :username", User.class)
                .setParameter("username", username);
        return Optional.ofNullable(query.getSingleResult());
    }

    ////my old method

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<User> query = entityManager
                .createQuery("SELECT u FROM User u JOIN FETCH u.roles roles where u.name = :name", User.class);
        query.setParameter("name", username);
        if (username == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = query.getSingleResult();
        return user;
    }

}
