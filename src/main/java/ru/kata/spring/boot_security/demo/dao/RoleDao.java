package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
     Optional<Role> getRoleID(int id);
     void saveRoleName(String name);

    Optional<Role> getRoleByName(String name);

    List<Role> getAllRoles();
    void add(Role role);


}
