package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        Optional<User> user = userDao.getUserID(id);
        if (user.isEmpty()) {
            throw new RuntimeException("user not found - " + id);
        }
        return user.get();
    }

    @Override
    public User findByUsername(String name) {
        Optional<User> user = userDao.loadUserByUsername(name);
        if (user.isEmpty()) {
            throw new RuntimeException("user not found - " + name);
        }
        return user.get();
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }


    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
//        getRoleById(user.getRoles());
        User userUp = getUserById(user.getId());
        if (userUp.getPassword().equals(user.getPassword())) {
            userDao.updateUser(user);
        } else {
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            userDao.updateUser(user);
        }
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userDao.deleteUserById(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleByName(String name) {
        Optional<Role> role = roleDao.getRoleByName(name);
        if (role.isEmpty()) {
            throw new RuntimeException("role not found");
        }
        return role.get();
    }

}
