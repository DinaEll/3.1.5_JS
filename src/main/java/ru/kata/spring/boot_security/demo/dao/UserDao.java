package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getAllUsers();

    void saveUser(User user);


    void updateUser(User user);

    void deleteUserById(int id);

    void deleteUserByName(User user);

    Optional<User> getUserID(int id);

    Optional<User> loadUserByUsername(String username);

    ////my old method
    User getUserById(int id);
    User findByUsername(String username);

}
