package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    void deleteUserById(int id);
    User getUserById(int id);
    User findByUsername(String name);
    UserDetails loadUserByUsername(String username);

    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);

    List<Role> getAllRoles();
    Role getRoleByName(String name);

}
